package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.BookingSlotDto;
import com.meow_care.meow_care_service.dto.BookingSlotTemplateDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.BookingSlot;
import com.meow_care.meow_care_service.entities.BookingSlotTemplate;
import com.meow_care.meow_care_service.entities.Service;
import com.meow_care.meow_care_service.entities.SitterProfile;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.BookingSlotStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.BookingSlotMapper;
import com.meow_care.meow_care_service.mapper.BookingSlotTemplateMapper;
import com.meow_care.meow_care_service.repositories.BookingSlotRepository;
import com.meow_care.meow_care_service.repositories.BookingSlotTemplateRepository;
import com.meow_care.meow_care_service.services.BookingSlotService;
import com.meow_care.meow_care_service.services.ServiceEntityService;
import com.meow_care.meow_care_service.services.SitterProfileService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import com.meow_care.meow_care_service.util.UserUtils;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class BookingSlotServiceImpl extends BaseServiceImpl<BookingSlotDto, BookingSlot, BookingSlotRepository, BookingSlotMapper> implements BookingSlotService {

    private final ServiceEntityService serviceEntityService;

    private final SitterProfileService sitterProfileService;

    private final BookingSlotTemplateRepository bookingSlotTemplateRepository;

    private final BookingSlotTemplateMapper bookingSlotTemplateMapper;

    public BookingSlotServiceImpl(BookingSlotRepository repository, BookingSlotMapper mapper, ServiceEntityService serviceEntityService, SitterProfileService sitterProfileService, BookingSlotTemplateRepository bookingSlotTemplateRepository, BookingSlotTemplateMapper bookingSlotTemplateMapper) {
        super(repository, mapper);
        this.serviceEntityService = serviceEntityService;
        this.sitterProfileService = sitterProfileService;
        this.bookingSlotTemplateRepository = bookingSlotTemplateRepository;
        this.bookingSlotTemplateMapper = bookingSlotTemplateMapper;
    }

    //method to create booking slot
    @Override
    public ApiResponse<BookingSlotTemplateDto> create(BookingSlotTemplateDto dto) {

        //check start time is not between other booking slot
        boolean startTimeConflict = repository.existsByTimeBetweenStartTimeAndEndTime(dto.startTime());
        if (startTimeConflict) {
            throw new ApiException(ApiStatus.CONFLICT, "Start time is between other booking slot");
        }

        //check end time is not between other booking slot
        boolean endTimeConflict = repository.existsByTimeBetweenStartTimeAndEndTime(dto.endTime());
        if (endTimeConflict) {
            throw new ApiException(ApiStatus.CONFLICT, "End time is between other booking slot");
        }

        //auto create time slot for each date in next 3 month from dto start time and end time
        Instant startTime = dto.startTime();
        Instant endTime = dto.endTime();
        Instant currentDate = startTime;

        //get sitter profile
        SitterProfile sitterProfile = sitterProfileService.getEntityByUserId(UserUtils.getCurrentUserId())
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND, "Sitter profile not found"));

        String name = dto.startTime().atZone(ZoneId.of("GMT+7")).getHour() + ":" + dto.startTime().atZone(ZoneId.of("GMT+7")).getMinute() + " - " + dto.endTime().atZone(ZoneId.of("GMT+7")).getHour() + ":" + dto.endTime().atZone(ZoneId.of("GMT+7")).getMinute();

        BookingSlotTemplate bookingSlotTemplate = BookingSlotTemplate.builder()
                .sitterProfile(sitterProfile)
                .name(name)
                .startTime(dto.startTime())
                .endTime(dto.endTime())
                .build();
        bookingSlotTemplate = bookingSlotTemplateRepository.save(bookingSlotTemplate);


        while (currentDate.isBefore(startTime.plus(90, ChronoUnit.DAYS))) {
            BookingSlot bookingSlot = new BookingSlot();
            bookingSlot.setBookingSlotTemplate(bookingSlotTemplate);
            bookingSlot.setStartTime(currentDate);
            bookingSlot.setEndTime(currentDate.plus(Duration.between(startTime, endTime)));
            bookingSlot.setStatus(BookingSlotStatus.AVAILABLE);
            repository.save(bookingSlot);
            currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        }

        return ApiResponse.success(bookingSlotTemplateMapper.toDto(bookingSlotTemplate));
    }

    //method to assign service to booking slot
    @Override
    public ApiResponse<Void> assignService(UUID bookingSlotTemplateId, UUID serviceId) {
        BookingSlotTemplate bookingSlotTemplate = bookingSlotTemplateRepository.findById(bookingSlotTemplateId)
                .orElseThrow(() -> new ApiException(ApiStatus.NOT_FOUND, "Booking slot template not found"));

        Service service = serviceEntityService.findEntityById(serviceId);

        if (bookingSlotTemplate.getServices().add(service)) {
            bookingSlotTemplateRepository.save(bookingSlotTemplate);
        }

        return ApiResponse.success();
    }

    //get all booking slot template by user id
    @Override
    public ApiResponse<List<BookingSlotTemplateDto>> getAllByUserId(UUID userId) {
        List<BookingSlotTemplate> bookingSlotTemplates = bookingSlotTemplateRepository.findBySitterProfile_User_Id(userId);

        return ApiResponse.success(bookingSlotTemplateMapper.toDtoList(bookingSlotTemplates));
    }

    @Override
    public ApiResponse<List<BookingSlotTemplateDto>> getByServiceIdAndTime(UUID serviceId, Instant startDate, Instant endDate) {
        List<BookingSlotTemplate> bookingSlotTemplates = bookingSlotTemplateRepository.findByServices_Id(serviceId);

        // Use an iterator to safely remove elements from the list
        Iterator<BookingSlotTemplate> iterator = bookingSlotTemplates.iterator();
        while (iterator.hasNext()) {
            BookingSlotTemplate bookingSlotTemplate = iterator.next();
            List<BookingSlot> bookingSlots = repository.findByBookingSlotTemplate_IdAndStartTimeBetween(bookingSlotTemplate.getId(), startDate, endDate);
            for (BookingSlot bookingSlot : bookingSlots) {
                if (bookingSlot.getStatus() != BookingSlotStatus.AVAILABLE) {
                    iterator.remove();
                    break;
                }
            }
        }

        return ApiResponse.success(bookingSlotTemplateMapper.toDtoList(bookingSlotTemplates));
    }

}
