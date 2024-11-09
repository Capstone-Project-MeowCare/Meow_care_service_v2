package com.meow_care.meow_care_service.services;

import com.meow_care.meow_care_service.dto.ReportDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Report;
import com.meow_care.meow_care_service.services.base.BaseService;

import java.util.List;
import java.util.UUID;

public interface ReportService extends BaseService<ReportDto, Report> {

    //get by report type id
    ApiResponse<List<ReportDto>> getByReportTypeId(UUID reportTypeId);

    //get by user id
    ApiResponse<List<ReportDto>> getByUserId(UUID userId);

}
