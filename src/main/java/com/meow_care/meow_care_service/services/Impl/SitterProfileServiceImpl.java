package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ProfilePictureDto;
import com.meow_care.meow_care_service.dto.SitterProfileDto;
import com.meow_care.meow_care_service.dto.SitterProfileWithUserDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Certificate;
import com.meow_care.meow_care_service.entities.ProfilePicture;
import com.meow_care.meow_care_service.entities.SitterFormRegister;
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
import com.meow_care.meow_care_service.services.SitterProfileService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class SitterProfileServiceImpl extends BaseServiceImpl<SitterProfileDto, SitterProfile, SitterProfileRepository, SitterProfileMapper> implements SitterProfileService {

    private final ProfilePictureMapper profilePictureMapper;

    private final ProfilePictureRepository profilePictureRepository;

    public SitterProfileServiceImpl(SitterProfileRepository repository, SitterProfileMapper mapper, ProfilePictureMapper profilePictureMapper, ProfilePictureRepository profilePictureRepository) {
        super(repository, mapper);
        this.profilePictureMapper = profilePictureMapper;
        this.profilePictureRepository = profilePictureRepository;
    }

    @Override
    public ApiResponse<SitterProfileDto> create(SitterFormRegister sitterFormRegister) {
        SitterProfile sitterProfile = new SitterProfile();
        sitterProfile.setUser(sitterFormRegister.getUser());
        Set<Certificate> certificates = sitterFormRegister.getCertificates();
        certificates.forEach(certificate -> certificate.setSitterProfile(sitterProfile));
        sitterProfile.setCertificates(certificates);
        SitterProfile saveSitterProfile = repository.save(sitterProfile);
        return ApiResponse.created(mapper.toDto(saveSitterProfile));
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

        //count number of cargo
        int count = dto.profilePictures().stream().filter(ProfilePictureDto::isCargoProfilePicture).toArray().length;

        mapper.partialUpdate(dto, sitterProfile);
        sitterProfile.setMaximumQuantity(count);
        sitterProfile = repository.save(sitterProfile);

        return ApiResponse.updated(mapper.toDto(sitterProfile));
    }

    @Override
    public ApiResponse<Void> updateStatusById(SitterProfileStatus status, UUID id) {
        int updated = repository.updateStatusById(status, id);
        if (updated == 0) {
            throw new ApiException(ApiStatus.UPDATE_ERROR, "Failed to update status");
        }
        return ApiResponse.updated();
    }

    @Override
    public ApiResponse<Page<SitterProfileDto>> search(double latitude, double longitude, String name, Pageable pageable) {
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
}
