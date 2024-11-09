package com.meow_care.meow_care_service.services.Impl;

import com.meow_care.meow_care_service.dto.ReportDto;
import com.meow_care.meow_care_service.dto.response.ApiResponse;
import com.meow_care.meow_care_service.entities.Report;
import com.meow_care.meow_care_service.mapper.ReportMapper;
import com.meow_care.meow_care_service.repositories.ReportRepository;
import com.meow_care.meow_care_service.services.ReportService;
import com.meow_care.meow_care_service.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReportServiceImpl extends BaseServiceImpl<ReportDto, Report, ReportRepository, ReportMapper> implements ReportService {
    public ReportServiceImpl(ReportRepository repository, ReportMapper mapper) {
        super(repository, mapper);
    }


    @Override
    public ApiResponse<List<ReportDto>> getByReportTypeId(UUID reportTypeId) {
        List<Report> reports = repository.findByReportTypeId(reportTypeId);
        return ApiResponse.success(mapper.toDtoList(reports));
    }

    @Override
    public ApiResponse<List<ReportDto>> getByUserId(UUID userId) {
        List<Report> reports = repository.findByUserId(userId);
        return ApiResponse.success(mapper.toDtoList(reports));
    }
}
