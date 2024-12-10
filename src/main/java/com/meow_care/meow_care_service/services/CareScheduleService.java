package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.care_schedule.CareScheduleDto;
import com.meow_care.meow_care_service.dto.care_schedule.CareScheduleWithTaskDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.CareSchedule;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.List;
import java.util.UUID;

public interface CareScheduleService extends BaseService<CareScheduleDto, CareSchedule> {

    //get by id with task
    ApiResponse<CareScheduleWithTaskDto> getWithTask(UUID id);

    CareSchedule createCareSchedule(UUID bookingId);

    CareSchedule createCareScheduleForBuyService(UUID bookingId);

    ApiResponse<CareScheduleWithTaskDto> getByBookingId(UUID bookingId);

    ApiResponse<List<CareScheduleWithTaskDto>> getBySitterId(UUID sitterId);
}
