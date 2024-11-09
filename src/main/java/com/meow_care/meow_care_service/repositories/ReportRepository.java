package com.meow_care.meow_care_service.repositories;

import com.meow_care.meow_care_service.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {

    List<Report> findByReportTypeId(UUID reportTypeId);

    List<Report> findByUserId(UUID userId);

}