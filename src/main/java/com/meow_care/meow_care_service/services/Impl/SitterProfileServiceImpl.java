package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ProfilePictureDto;
import com.meow_care.meow_care_service.dto.SitterProfileDto;
import com.meow_care.meow_care_service.dto.SitterProfileWithUserDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.dto.response.NominatimResponse;
import com.meow_care.meow_care_service.entities.ProfilePicture;
import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.SitterProfileStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.ProfilePictureMapper;
import com.meow_care.meow_care_service.mapper.SitterProfileMapper;
import com.meow_care.meow_care_service.projection.SitterProfileInfo;
import com.meow_care.meow_care_service.repositories.ProfilePictureRepository;
import com.meow_care.meow_care_service.repositories.SitterProfileRepository;
import com.meow_care.meow_care_service.repositories.client.NominatimClient;
import com.meow_care.meow_care_service.services.SitterProfileService;
import com.meow_care.meow_care_service.services.WalletService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SitterProfileServiceImpl extends BaseServiceImpl<SitterProfileDto, SitterProfile, SitterProfileRepository, SitterProfileMapper> implements SitterProfileService {

    private final ProfilePictureMapper profilePictureMapper;

    private final ProfilePictureRepository profilePictureRepository;

    private final WalletService walletService;

    private final NominatimClient  nominatimClient;

    public SitterProfileServiceImpl(SitterProfileRepository repository, SitterProfileMapper mapper, ProfilePictureMapper profilePictureMapper, ProfilePictureRepository profilePictureRepository, WalletService walletService, NominatimClient nominatimClient) {
        super(repository, mapper);
        this.profilePictureMapper = profilePictureMapper;
        this.profilePictureRepository = profilePictureRepository;
        this.walletService = walletService;
        this.nominatimClient = nominatimClient;
    }

    @Override
    public ApiResponse<SitterProfileDto> create(SitterProfileDto dto) {
        UUID userId = UserUtils.getCurrentUserId();
        if (repository.existsByUserId(userId)) {
            throw new ApiException(ApiStatus.ALREADY_EXISTS, "User already has a sitter profile");
        }

        SitterProfile sitterProfile = mapper.toEntity(dto);
        sitterProfile.setUser(User.builder().id(userId).build());
        sitterProfile.setStatus(SitterProfileStatus.INACTIVE);

        sitterProfile = repository.save(sitterProfile);

        return ApiResponse.created(mapper.toDto(sitterProfile));
    }

    @Override
    public ApiResponse<SitterProfileWithUserDto> getProfileWithUser(UUID id) {
        SitterProfile sitterProfile = repository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        return ApiResponse.success(mapper.toDtoWithUser(sitterProfile));
    }

    @Override
    public Optional<SitterProfile> getEntityByUserId(UUID id) {
        return repository.findByUserId(id);
    }

    @Override
    public SitterProfile getEntityBySitterId(UUID sitterId) {
        return repository.findByUserId(sitterId).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND, "Sitter profile not found"));
    }

    @Override
    public ApiResponse<SitterProfileDto> getBySitterId(UUID id) {
        SitterProfile sitterProfile = getEntityByUserId(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        return ApiResponse.success(mapper.toDto(sitterProfile));
    }

    @Override
    public ApiResponse<List<SitterProfileDto>> getAllByStatus(SitterProfileStatus status) {
        List<SitterProfile> sitterProfiles = repository.findByStatus(status);
        return ApiResponse.success(mapper.toDtoList(sitterProfiles));
    }

    @Override
    public ApiResponse<SitterProfileDto> update(UUID id, SitterProfileDto dto) {
        SitterProfile sitterProfile = repository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));


        mapper.partialUpdate(dto, sitterProfile);
        if(dto.maximumQuantity() != null) {
        }
        sitterProfile = repository.save(sitterProfile);

        if (dto.status() != null) {
            handleStatusUpdate(id, dto.status());
        }
        return ApiResponse.updated(mapper.toDto(sitterProfile));
    }

    @Override
    public ApiResponse<Void> updateStatusById(SitterProfileStatus status, UUID id) {
        int updated = repository.updateStatusById(status, id);
        if (updated == 0) {
            throw new ApiException(ApiStatus.UPDATE_ERROR, "Failed to update status");
        }
        handleStatusUpdate(id, status);
        return ApiResponse.updated();
    }

    @Override
    public ApiResponse<Page<SitterProfileDto>> search(double latitude, double longitude, String name, Pageable pageable) {
        NominatimResponse nominatimResponse = nominatimClient.reverseGeocoding(latitude, longitude);

        if (nominatimResponse == null || nominatimResponse.getDisplayName() == null) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Invalid request: missing or incorrect data.");
        }

        String location = nominatimResponse.getDisplayName();

        Page<SitterProfileInfo> sitterProfiles = repository.findAllWithDistanceAndName(latitude, longitude, name, pageable);
        return ApiResponse.success(sitterProfiles.map(mapper::toDto));
    }

    @Override
    public ApiResponse<SitterProfileDto> addProfilePictures(UUID id, List<ProfilePictureDto> pictureDtos) {
        SitterProfile sitterProfile = repository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        List<ProfilePicture> profilePictures = profilePictureMapper.toEntityList(pictureDtos);
        profilePictures.forEach(profilePicture -> profilePicture.setSitterProfile(sitterProfile));
        sitterProfile.getProfilePictures().addAll(profilePictures);
        SitterProfile saveSitterProfile = repository.save(sitterProfile);
        return ApiResponse.updated(mapper.toDto(saveSitterProfile));
    }

    @Override
    public ApiResponse<SitterProfileDto> removeProfilePicture(UUID id, List<ProfilePictureDto> pictureDtos) {
        List<ProfilePicture> profilePictures = profilePictureMapper.toEntityList(pictureDtos);
        profilePictureRepository.deleteAll(profilePictures);
        SitterProfile sitterProfile = repository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        return ApiResponse.updated(mapper.toDto(sitterProfile));
    }

    //handle update status by id
    private void handleStatusUpdate(UUID id, SitterProfileStatus status) {
        if (status == SitterProfileStatus.ACTIVE) {
            SitterProfile sitterProfile = repository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND, "Sitter profile not found"));

            // Define profile constraints as constants
            final int MIN_PICTURES = 4;
            final int MAX_PICTURES = 10;
            final int MAX_CAGE_IMAGES = 10;
            final int MAX_CERTIFICATES = 5;
            final int MIN_CARE_PETS = 0;
            final int MAX_CARE_PETS = 20;
            final int MIN_BALANCE_WALLET = 200000;


            // Validate profile pictures
            int profilePictureCount = sitterProfile.getProfilePictures().stream().filter(profilePicture -> !profilePicture.getIsCargoProfilePicture()).toArray().length;
            if (profilePictureCount < MIN_PICTURES || profilePictureCount > MAX_PICTURES) {
                throw new ApiException(ApiStatus.VALIDATION_ERROR, "Profile pictures must be between " + MIN_PICTURES + " and " + MAX_PICTURES);
            }


            // Validate cat cage images
            int cagePictureCount = (int) sitterProfile.getProfilePictures().stream().filter(ProfilePicture::getIsCargoProfilePicture).count();
            if (cagePictureCount < 1 || cagePictureCount > MAX_CAGE_IMAGES) {
                throw new ApiException(ApiStatus.VALIDATION_ERROR, "Cat cage images must be between 1 and " + MAX_CAGE_IMAGES);
            }

            // Validate certificates
            int certificateCount = sitterProfile.getCertificates().size();
            if (certificateCount > MAX_CERTIFICATES) {
                throw new ApiException(ApiStatus.VALIDATION_ERROR, "No more than " + MAX_CERTIFICATES + " certificates are allowed");
            }

            // Validate care capacity
            int petsCaredFor = sitterProfile.getMaximumQuantity();
            if (petsCaredFor < MIN_CARE_PETS || petsCaredFor > MAX_CARE_PETS) {
                throw new ApiException(ApiStatus.VALIDATION_ERROR, "Number of pets a sitter can care for must be between " + MIN_CARE_PETS + " and " + MAX_CARE_PETS);
            }

            // If all validations pass, activate profile
            sitterProfile.setStatus(SitterProfileStatus.ACTIVE);
            repository.save(sitterProfile);
        }
    }
}
