package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ProfilePictureDto;
import com.meow_care.meow_care_service.dto.SitterProfileDto;
import com.meow_care.meow_care_service.dto.SitterProfileWithUserDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.ProfilePicture;
import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.entities.User;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.ServiceType;
import com.meow_care.meow_care_service.enums.SitterProfileStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.ProfilePictureMapper;
import com.meow_care.meow_care_service.mapper.SitterProfileMapper;
import com.meow_care.meow_care_service.projection.SitterProfileProjection;
import com.meow_care.meow_care_service.repositories.ProfilePictureRepository;
import com.meow_care.meow_care_service.repositories.ReviewRepository;
import com.meow_care.meow_care_service.repositories.SitterProfileRepository;
import com.meow_care.meow_care_service.repositories.specification.SitterProfileSpecifications;
import com.meow_care.meow_care_service.services.SitterProfileService;
import com.meow_care.meow_care_service.services.WalletService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SitterProfileServiceImpl extends BaseServiceImpl<SitterProfileDto, SitterProfile, SitterProfileRepository, SitterProfileMapper> implements SitterProfileService {

    private final ProfilePictureMapper profilePictureMapper;

    private final ProfilePictureRepository profilePictureRepository;

    private final WalletService walletService;

    private final ReviewRepository reviewRepository;

    public SitterProfileServiceImpl(SitterProfileRepository repository, SitterProfileMapper mapper, ProfilePictureMapper profilePictureMapper, ProfilePictureRepository profilePictureRepository, WalletService walletService, ReviewRepository reviewRepository) {
        super(repository, mapper);
        this.profilePictureMapper = profilePictureMapper;
        this.profilePictureRepository = profilePictureRepository;
        this.walletService = walletService;
        this.reviewRepository = reviewRepository;
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
        sitterProfile.setRating(5.0);

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
        sitterProfile.setNumberOfReview(reviewRepository.countByBookingOrder_Sitter_Id(sitterProfile.getUser().getId()));
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
        sitterProfile = repository.save(sitterProfile);

        if (dto.getStatus() != null) {
            handleStatusUpdate(id, dto.getStatus());
        }
        return ApiResponse.updated(mapper.toDto(sitterProfile));
    }

    @Override
    public ApiResponse<Void> updateStatusById(SitterProfileStatus status, UUID id) {
        SitterProfile sitterProfile = repository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        handleStatusUpdate(id, status);
        sitterProfile.setStatus(status);
        repository.save(sitterProfile);
        return ApiResponse.updated();
    }

    @Override
    public SitterProfile updateRating(UUID id, double rating) {
        SitterProfile sitterProfile = repository.findById(id).orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND));
        sitterProfile.setRating(rating);
        return repository.save(sitterProfile);
    }

    @Override
    public ApiResponse<Page<SitterProfileDto>> search(Double latitude, Double longitude, ServiceType serviceType, LocalDate startTime, LocalDate endTime, BigDecimal minPrice, BigDecimal maxPrice, Integer minQuantity, Pageable pageable) {
        List<SitterProfileProjection> sitterProfileProjections = repository.findBy(SitterProfileSpecifications
                .search(latitude, longitude, serviceType, startTime, endTime, minPrice, maxPrice, minQuantity), q -> q
                .as(SitterProfileProjection.class)
                .all()
        );

        Map<UUID, Double> distances;
        if (latitude != null && longitude != null) {
            //Calculate distance and sort by distance
            distances = sitterProfileProjections.stream()
                    .collect(Collectors.toMap(
                            SitterProfileProjection::getId,
                            profile -> calculateDistance(latitude, longitude, profile.getLatitude(), profile.getLongitude())
                    ))
                    .entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));
        } else {
            // If latitude or longitude is null, just collect the IDs without sorting by distance
            distances = sitterProfileProjections.stream()
                    .collect(Collectors.toMap(
                            SitterProfileProjection::getId,
                            profile -> 0.0, // Default distance value
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));
        }


        //Fetch full data for paginated profiles
        List<UUID> ids = new ArrayList<>(distances.keySet());
        List<SitterProfile> fullDataProfiles = repository.findByIdIn(ids);

        fullDataProfiles.forEach(profile -> profile.setDistance(distances.get(profile.getId())));

        fullDataProfiles.sort(Comparator.comparingDouble(SitterProfile::getDistance));

        //Map to DTO
        List<SitterProfileDto> sitterProfileDtos = mapper.toDtoList(fullDataProfiles);
        sitterProfileDtos.forEach(sitterProfileDto -> sitterProfileDto.setNumberOfReview(reviewRepository.countByBookingOrder_Sitter_Id(sitterProfileDto.getSitterId())));

        // 3. Manually select the sublist for the current page
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), sitterProfileDtos.size());

        List<SitterProfileDto> pagedList;
        if (start > sitterProfileDtos.size()) {
            // If the start offset is beyond the size, return an empty list
            pagedList = Collections.emptyList();
        } else {
            pagedList = sitterProfileDtos.subList(start, end);
        }

        // 4. Create a Page object
        Page<SitterProfileDto> resultPage = new PageImpl<>(pagedList, pageable, sitterProfileDtos.size());

        // Return the paginated response
        return ApiResponse.success(resultPage);
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

            // Validate wallet balance
            BigDecimal walletBalance = walletService.getBalance(sitterProfile.getUser().getId());
            if (walletBalance.compareTo(BigDecimal.valueOf(MIN_BALANCE_WALLET)) < 0) {
                throw new ApiException(ApiStatus.VALIDATION_ERROR, "Wallet balance must be at least " + MIN_BALANCE_WALLET);
            }


            // If all validations pass, activate profile
            sitterProfile.setStatus(SitterProfileStatus.ACTIVE);
            repository.save(sitterProfile);
        }
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double EARTH_RADIUS = 6371; // Radius in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                   + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                     * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
}
