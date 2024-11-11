package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.care_schedule.CareScheduleDto;
import com.meow_care.meow_care_service.dto.care_schedule.CareScheduleWithTaskDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.BookingDetail;
import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.entities.CareSchedule;
import com.meow_care.meow_care_service.entities.ConfigService;
import com.meow_care.meow_care_service.entities.PetProfile;
import com.meow_care.meow_care_service.entities.Service;
import com.meow_care.meow_care_service.entities.Task;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.CareScheduleMapper;
import com.meow_care.meow_care_service.repositories.CareScheduleRepository;
import com.meow_care.meow_care_service.services.CareScheduleService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class CareScheduleServiceImpl extends BaseServiceImpl<CareScheduleDto, CareSchedule, CareScheduleRepository, CareScheduleMapper>
        implements CareScheduleService {

    public CareScheduleServiceImpl(CareScheduleRepository repository, CareScheduleMapper mapper) {
        super(repository, mapper);
    }

    @Override
    public ApiResponse<CareScheduleWithTaskDto> getWithTask(UUID id) {
        CareSchedule careSchedule = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Care schedule not found with ID: " + id)
        );
        return ApiResponse.success(mapper.toDtoWithTask(careSchedule));
    }

    @Override
    public CareSchedule createCareSchedule(BookingOrder bookingOrder) {
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

            if (service == null) {
                continue;  // Skip if service is not found
            }

            // Collect all pet profiles that share this service
            Set<PetProfile> petProfiles = detailsForService.stream()
                    .map(BookingDetail::getPet)
                    .collect(Collectors.toSet());

            // Create tasks for each day within the schedule's start and end dates
            tasks.addAll(createDailyMergedTasks(service, careSchedule, bookingOrder.getStartDate(), bookingOrder.getEndDate(), petProfiles));
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
        return ApiResponse.success(mapper.toDtoWithTask(careSchedule));
    }

    @Override
    public ApiResponse<List<CareScheduleWithTaskDto>> getBySitterId(UUID sitterId) {
        List<CareSchedule> careSchedules = repository.findByBookingSitterId(sitterId);
        return ApiResponse.success(mapper.toDtoWithTask(careSchedules));
    }

    private List<Task> createDailyMergedTasks(Service service, CareSchedule careSchedule, Instant scheduleStart, Instant scheduleEnd, Set<PetProfile> petProfiles) {
        List<Task> tasks = new ArrayList<>();

        // Define task start hour and duration from the Service
        int startHour = service.getStartTime();
        int durationMinutes = service.getDuration();

        ZoneId zoneId = ZoneId.of("GMT+7");

        // Convert schedule start and end to LocalDate for daily iteration
        LocalDate startDate = scheduleStart.atZone(zoneId).toLocalDate();
        LocalDate endDate = scheduleEnd.atZone(zoneId).toLocalDate();

        // Create tasks for each day within the schedule range
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // Create a single task for the service on this day, shared by all pets with this service
            Task task = createTask(service, careSchedule, date, startHour, durationMinutes, petProfiles);
            tasks.add(task);
        }

        return tasks;
    }

    private Task createTask(Service service, CareSchedule careSchedule, LocalDate date, int startHour, int durationMinutes, Set<PetProfile> petProfiles) {
        LocalTime startTime = LocalTime.of(startHour, 0);
        ZonedDateTime taskStartZonedDateTime = ZonedDateTime.of(date, startTime, ZoneId.systemDefault());
        Instant taskStartTime = taskStartZonedDateTime.toInstant();
        Instant taskEndTime = taskStartTime.plus(durationMinutes, ChronoUnit.MINUTES);
        ConfigService configService = service.getConfigService();

        Task task = new Task();
        task.setSession(careSchedule);  // Associate with the CareSchedule
        task.setDescription(configService.getName() + ": " + configService.getActionDescription());
        task.setStartTime(taskStartTime);
        task.setEndTime(taskEndTime);
        task.setStatus(0);  // Initial status, e.g., 0 for pending
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        task.setPetProfiles(petProfiles);  // Associate all pets sharing the service

        return task;
    }

}
