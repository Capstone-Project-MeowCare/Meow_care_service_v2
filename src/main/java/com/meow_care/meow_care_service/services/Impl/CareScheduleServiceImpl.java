package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.care_schedule.CareScheduleDto;
import com.meow_care.meow_care_service.dto.care_schedule.CareScheduleWithTaskDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.BookingDetail;
import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.entities.CareSchedule;
import com.meow_care.meow_care_service.entities.PetProfile;
import com.meow_care.meow_care_service.entities.Service;
import com.meow_care.meow_care_service.entities.Task;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.ServiceType;
import com.meow_care.meow_care_service.enums.TaskStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.CareScheduleMapper;
import com.meow_care.meow_care_service.repositories.BookingOrderRepository;
import com.meow_care.meow_care_service.repositories.CareScheduleRepository;
import com.meow_care.meow_care_service.services.CareScheduleService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class CareScheduleServiceImpl extends BaseServiceImpl<CareScheduleDto, CareSchedule, CareScheduleRepository, CareScheduleMapper>
        implements CareScheduleService {

    private final BookingOrderRepository bookingOrderRepository;

    public CareScheduleServiceImpl(CareScheduleRepository repository, CareScheduleMapper mapper, BookingOrderRepository bookingOrderRepository) {
        super(repository, mapper);
        this.bookingOrderRepository = bookingOrderRepository;
    }

    @Override
    public ApiResponse<CareScheduleWithTaskDto> getWithTask(UUID id) {
        CareSchedule careSchedule = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Care schedule not found with ID: " + id)
        );
        return ApiResponse.success(mapper.toDtoWithTask(careSchedule));
    }

    @Override
    public CareSchedule createCareSchedule(UUID bookingId) {
        // Find the BookingOrder by ID
        BookingOrder bookingOrder = bookingOrderRepository.findById(bookingId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Booking order not found with ID: " + bookingId)
        );

        // Initialize the CareSchedule
        CareSchedule careSchedule = new CareSchedule();
        careSchedule.setBooking(bookingOrder);
        careSchedule.setStartTime(bookingOrder.getStartDate());
        careSchedule.setEndTime(bookingOrder.getEndDate());
        careSchedule.setCreatedAt(Instant.now());
        careSchedule.setUpdatedAt(Instant.now());

        // Group booking details by Service ID to find shared services among pets
        Map<UUID, List<BookingDetail>> serviceToBookingDetails = bookingOrder.getBookingDetails().stream()
                .collect(Collectors.groupingBy(detail -> detail.getService().getId()));

        Set<Task> tasks = new LinkedHashSet<>();

        // Generate tasks for each unique service, merging tasks if multiple pets share the same service
        for (Map.Entry<UUID, List<BookingDetail>> entry : serviceToBookingDetails.entrySet()) {
            List<BookingDetail> detailsForService = entry.getValue();

            // Get the service from any booking detail in this group (all are the same service)
            Service service = detailsForService.get(0).getService();

            if (service == null || service.getServiceType().equals(ServiceType.MAIN_SERVICE) || service.getServiceType().equals(ServiceType.ADDITION_SERVICE)) {
                continue;  // Skip if service is not found and is basic service
            }

            // Collect all pet profiles that share this service
            Set<PetProfile> petProfiles = detailsForService.stream()
                    .map(BookingDetail::getPet)
                    .collect(Collectors.toSet());

            // Create tasks for each day within the schedule's start and end dates
            tasks.addAll(createDailyMergedTasks(bookingOrder.getSitter().getId(), service, careSchedule, bookingOrder.getStartDate(), bookingOrder.getEndDate(), petProfiles));
        }

        // Attach the tasks to the CareSchedule
        careSchedule.setTasks(tasks);

        // Save the CareSchedule
        careSchedule = repository.save(careSchedule);
        return careSchedule;
    }

    @Override
    public ApiResponse<CareScheduleWithTaskDto> getByBookingId(UUID bookingId) {
        CareSchedule careSchedule = repository.findByBookingId(bookingId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Care schedule not found for booking ID: " + bookingId)
        );

        careSchedule.setTasks(careSchedule.getTasks().stream().sorted(Comparator.comparing(Task::getStartTime)).collect(Collectors.toCollection(LinkedHashSet::new)));

        return ApiResponse.success(mapper.toDtoWithTask(careSchedule));
    }

    @Override
    public ApiResponse<List<CareScheduleWithTaskDto>> getBySitterId(UUID sitterId) {
        List<CareSchedule> careSchedules = repository.findByBookingSitterId(sitterId);
        return ApiResponse.success(mapper.toDtoWithTask(careSchedules));
    }

    private List<Task> createDailyMergedTasks(UUID sitterId, Service service, CareSchedule careSchedule, Instant scheduleStart, Instant scheduleEnd, Set<PetProfile> petProfiles) {
        List<Task> tasks = new ArrayList<>();
        int startHour = service.getStartTime();
        int endHour = service.getEndTime();
        ZoneId gmtPlus7 = ZoneId.of("GMT+7");

        // Convert scheduleStart and scheduleEnd to GMT+7
        LocalDate startDate = scheduleStart.atZone(gmtPlus7).toLocalDate();
        LocalDate endDate = scheduleEnd.atZone(gmtPlus7).toLocalDate();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            for (PetProfile petProfile : petProfiles) {
                tasks.add(createTask(sitterId, service, careSchedule, date, startHour, endHour, petProfile));
            }
        }
        return tasks;
    }

    private Task createTask(UUID sitterId, Service service, CareSchedule careSchedule, LocalDate date, int startHour, int endHour, PetProfile petProfile) {
        ZoneId gmtPlus7 = ZoneId.of("GMT+7");
        ZoneId utc = ZoneId.of("UTC");

        // Create ZonedDateTime in GMT+7
        ZonedDateTime startZonedDateTimeGmt7 = ZonedDateTime.of(date, LocalTime.of(startHour, 0), gmtPlus7);
        ZonedDateTime endZonedDateTimeGmt7 = ZonedDateTime.of(date, LocalTime.of(endHour, 0), gmtPlus7);

        // Convert to UTC
        ZonedDateTime startZonedDateTimeUtc = startZonedDateTimeGmt7.withZoneSameInstant(utc);
        ZonedDateTime endZonedDateTimeUtc = endZonedDateTimeGmt7.withZoneSameInstant(utc);

        Task task = new Task();
        task.setAssigneeId(sitterId);
        task.setSession(careSchedule);
        task.setPetProfile(petProfile);
        task.setName(service.getName());
        task.setDescription(service.getActionDescription());
        task.setStartTime(startZonedDateTimeUtc.toInstant());
        task.setEndTime(endZonedDateTimeUtc.toInstant());
        task.setStatus(TaskStatus.PENDING);
        return task;
    }

}

