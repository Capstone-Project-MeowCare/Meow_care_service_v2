package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.care_schedule.CareScheduleDto;
import com.meow_care.meow_care_service.dto.care_schedule.CareScheduleWithTaskDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.BookingDetail;
import com.meow_care.meow_care_service.entities.BookingOrder;
import com.meow_care.meow_care_service.entities.BookingSlot;
import com.meow_care.meow_care_service.entities.CareSchedule;
import com.meow_care.meow_care_service.entities.PetProfile;
import com.meow_care.meow_care_service.entities.Service;
import com.meow_care.meow_care_service.entities.Task;
import com.meow_care.meow_care_service.enums.ApiStatus;
import com.meow_care.meow_care_service.enums.OrderType;
import com.meow_care.meow_care_service.enums.ServiceType;
import com.meow_care.meow_care_service.enums.TaskStatus;
import com.meow_care.meow_care_service.exception.ApiException;
import com.meow_care.meow_care_service.mapper.CareScheduleMapper;
import com.meow_care.meow_care_service.repositories.BookingOrderRepository;
import com.meow_care.meow_care_service.repositories.BookingSlotRepository;
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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class CareScheduleServiceImpl
        extends BaseServiceImpl<CareScheduleDto, CareSchedule, CareScheduleRepository, CareScheduleMapper>
        implements CareScheduleService {

    private final BookingOrderRepository bookingOrderRepository;

    private final BookingSlotRepository bookingSlotRepository;

    public CareScheduleServiceImpl(CareScheduleRepository repository, CareScheduleMapper mapper,
                                   BookingOrderRepository bookingOrderRepository, BookingSlotRepository bookingSlotRepository) {
        super(repository, mapper);
        this.bookingOrderRepository = bookingOrderRepository;
        this.bookingSlotRepository = bookingSlotRepository;
    }

    @Override
    public ApiResponse<CareScheduleWithTaskDto> getWithTask(UUID id) {
        CareSchedule careSchedule = repository.findById(id).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Care schedule not found with ID: " + id));
        return ApiResponse.success(mapper.toDtoWithTask(careSchedule));
    }

    @Override
    public CareSchedule createCareSchedule(UUID bookingId) {
        // Find the BookingOrder by ID
        BookingOrder bookingOrder = bookingOrderRepository.findById(bookingId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Booking order not found with ID: " + bookingId));

        // Initialize the CareSchedule
        CareSchedule careSchedule = new CareSchedule();
        careSchedule.setBooking(bookingOrder);
        careSchedule.setStartTime(bookingOrder.getStartDate());
        careSchedule.setEndTime(bookingOrder.getEndDate());
        careSchedule.setCreatedAt(Instant.now());
        careSchedule.setUpdatedAt(Instant.now());


//        // Group booking details by Service ID to find shared services among pets
//        Map<UUID, List<BookingDetail>> serviceToBookingDetails = bookingOrder.getBookingDetails().stream()
//                .collect(Collectors.groupingBy(detail -> detail.getService().getId()));
//
//        Map<Task, TimeRange> mainTaskTimeRanges = new HashMap<>();
//
//        // Generate tasks for each unique service, merging tasks if multiple pets share
//        // the same service
//        for (Map.Entry<UUID, List<BookingDetail>> entry : serviceToBookingDetails.entrySet()) {
//            List<BookingDetail> detailsForService = entry.getValue();
//
//            // Get the service from any booking detail in this group (all are the same
//            // service)
//            Service service = detailsForService.get(0).getService();
//
//            if (service == null || service.getServiceType().equals(ServiceType.MAIN_SERVICE)
//                    || service.getServiceType().equals(ServiceType.ADDITION_SERVICE)) {
//                continue; // Skip if service is not found and is basic service
//            }
//
//            // Collect all pet profiles that share this service
//            Set<PetProfile> petProfiles = detailsForService.stream()
//                    .map(BookingDetail::getPet)
//                    .collect(Collectors.toSet());
//
//            // Create tasks for each day within the schedule's start and end dates
//            tasks.addAll(createDailyMergedTasks(bookingOrder.getSitter().getId(), service, careSchedule,
//                    bookingOrder.getStartDate(), bookingOrder.getEndDate(), petProfiles));
//        }

        ZoneId gmtPlus7 = ZoneId.of("GMT+7");
        ZoneId utc = ZoneId.of("UTC");

        // Convert scheduleStart and scheduleEnd to GMT+7
        LocalDate startDate = bookingOrder.getStartDate().atZone(gmtPlus7).toLocalDate();
        LocalDate endDate = bookingOrder.getEndDate().atZone(gmtPlus7).toLocalDate();

        Set<Task> tasks = new LinkedHashSet<>();
        Map<LocalDate, List<Task>> tasksByDate = new HashMap<>();

        bookingOrder.getBookingDetails()
                .stream().filter(bookingDetail -> bookingDetail.getService().getServiceType().equals(ServiceType.CHILD_SERVICE))
                .forEach(bookingDetail -> {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {

                // Create ZonedDateTime in GMT+7
                ZonedDateTime startZonedDateTimeGmt7 = ZonedDateTime.of(date, bookingDetail.getService().getStartTime(), gmtPlus7);
                ZonedDateTime endZonedDateTimeGmt7 = ZonedDateTime.of(date, bookingDetail.getService().getEndTime(), gmtPlus7);

                // Convert to UTC
                ZonedDateTime startZonedDateTimeUtc = startZonedDateTimeGmt7.withZoneSameInstant(utc);
                ZonedDateTime endZonedDateTimeUtc = endZonedDateTimeGmt7.withZoneSameInstant(utc);

                Task task = new Task();
                task.setSession(careSchedule);
                task.setPetProfile(bookingDetail.getPet());
                task.setName(bookingDetail.getService().getName());
                task.setDescription(bookingDetail.getService().getActionDescription());
                task.setStartTime(startZonedDateTimeUtc.toInstant());
                task.setEndTime(endZonedDateTimeUtc.toInstant());
                task.setStatus(TaskStatus.PENDING);
                task.setAssigneeId(bookingOrder.getSitter().getId());

                tasksByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(task);

                tasks.add(task);
            }
        });

        List<Task> additionTasks = new ArrayList<>();
        //create addition tasks
        bookingOrder.getBookingDetails()
                .stream().filter(bookingDetail -> bookingDetail.getService().getServiceType().equals(ServiceType.ADDITION_SERVICE))
                .forEach(bookingDetail -> {
                    BookingSlot bookingSlot = bookingSlotRepository.getReferenceById(bookingDetail.getBookingSlotId());

                    Task additionTask = new Task();
                    additionTask.setSession(careSchedule);
                    additionTask.setPetProfile(bookingDetail.getPet());
                    additionTask.setName(bookingDetail.getService().getName());
                    additionTask.setDescription(bookingDetail.getService().getActionDescription());
                    additionTask.setStartTime(bookingSlot.getStartTime());
                    additionTask.setEndTime(bookingSlot.getEndTime());
                    additionTask.setStatus(TaskStatus.PENDING);
                    additionTask.setAssigneeId(bookingOrder.getSitter().getId());
                    additionTasks.add(additionTask);

                    // Get date of addition task in GMT+7
                    LocalDate additionTaskDate = additionTask.getStartTime()
                            .atZone(utc)
                            .withZoneSameInstant(gmtPlus7)
                            .toLocalDate();

                    // Find matching main task
                    List<Task> mainTasks = tasksByDate.getOrDefault(additionTaskDate, new ArrayList<>());
                    boolean matched = false;

                    for (Task mainTask : mainTasks) {
                        if (isTimeOverlap(mainTask.getStartTime(), mainTask.getEndTime(),
                                additionTask.getStartTime(), additionTask.getEndTime())) {
                            additionTask.setTaskParent(mainTask);
                            mainTask.getTasks().add(additionTask);
                            matched = true;
                            break;
                        }
                    }

                    if (!matched) {
                        tasks.add(additionTask);
                    }

                });


        // Attach the tasks to the CareSchedule
        careSchedule.setTasks(tasks);

        // Save the CareSchedule
        CareSchedule savedSchedule = repository.save(careSchedule);
        return savedSchedule;
    }

    private boolean isTimeOverlap(Instant start1, Instant end1, Instant start2, Instant end2) {
        return !start1.isAfter(end2) && !end1.isBefore(start2);
    }

    @Override
    public CareSchedule createCareScheduleForBuyService(UUID bookingId) {
        BookingOrder bookingOrder = bookingOrderRepository.findById(bookingId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Booking order not found with ID: " + bookingId));

        if (bookingOrder.getOrderType() != OrderType.BUY_SERVICE) {
            throw new ApiException(ApiStatus.INVALID_REQUEST, "Booking order is not a buy service order");
        }

        CareSchedule careSchedule = new CareSchedule();
        careSchedule.setBooking(bookingOrder);
        careSchedule.setStartTime(bookingOrder.getStartDate());

        Set<Task> tasks = new LinkedHashSet<>(createTasksForBuyService(careSchedule, bookingOrder));
        careSchedule.setTasks(tasks);
        careSchedule = repository.save(careSchedule);

        return careSchedule;
    }

    @Override
    public ApiResponse<CareScheduleWithTaskDto> getByBookingId(UUID bookingId) {
        CareSchedule careSchedule = repository.findByBookingId(bookingId).orElseThrow(
                () -> new ApiException(ApiStatus.NOT_FOUND, "Care schedule not found for booking ID: " + bookingId));

        careSchedule.setTasks(careSchedule.getTasks().stream().sorted(Comparator.comparing(Task::getStartTime))
                .collect(Collectors.toCollection(LinkedHashSet::new)));

        return ApiResponse.success(mapper.toDtoWithTask(careSchedule));
    }

    @Override
    public ApiResponse<List<CareScheduleWithTaskDto>> getBySitterId(UUID sitterId) {
        List<CareSchedule> careSchedules = repository.findByBookingSitterId(sitterId);
        return ApiResponse.success(mapper.toDtoWithTask(careSchedules));
    }

    private List<Task> createDailyMergedTasks(UUID sitterId, Service service, CareSchedule careSchedule,
            Instant scheduleStart, Instant scheduleEnd, Set<PetProfile> petProfiles) {
        List<Task> tasks = new ArrayList<>();
        LocalTime startHour = service.getStartTime();
        LocalTime endHour = service.getEndTime();
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

    private Task createTask(UUID sitterId, Service service, CareSchedule careSchedule, LocalDate date, LocalTime startHour, LocalTime endHour, PetProfile petProfile) {
        ZoneId gmtPlus7 = ZoneId.of("GMT+7");
        ZoneId utc = ZoneId.of("UTC");

        // Create ZonedDateTime in GMT+7
        ZonedDateTime startZonedDateTimeGmt7 = ZonedDateTime.of(date, startHour, gmtPlus7);
        ZonedDateTime endZonedDateTimeGmt7 = ZonedDateTime.of(date, endHour, gmtPlus7);

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

    private Set<Task> createTasksForBuyService(CareSchedule careSchedule, BookingOrder bookingOrder) {
        Set<Task> tasks = new LinkedHashSet<>();

        // Create task for each booking detail
        for (BookingDetail detail : bookingOrder.getBookingDetails()) {

            BookingSlot bookingSlot = bookingSlotRepository.getReferenceById(detail.getBookingSlotId());

            Task task = new Task();
            task.setSession(careSchedule);
            task.setPetProfile(detail.getPet());
            task.setName(detail.getService().getName());
            task.setDescription(detail.getService().getActionDescription());
            task.setStartTime(bookingSlot.getStartTime());
            task.setEndTime(bookingSlot.getEndTime());
            task.setStatus(TaskStatus.PENDING);
            task.setAssigneeId(bookingOrder.getSitter().getId());
            tasks.add(task);
        }

        return tasks;
    }

    private static class TimeRange {
        private final Instant startTime;
        private final Instant endTime;

        public TimeRange(Instant startTime, Instant endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public boolean overlaps(Instant otherStart, Instant otherEnd) {
            return !(otherEnd.isBefore(startTime) || otherStart.isAfter(endTime));
        }
    }
}
