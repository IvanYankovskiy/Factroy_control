package com.factory.control.repository;

import com.factory.control.domain.entities.ExtruderTelemetryReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtruderTelemetryReportRepository extends JpaRepository<ExtruderTelemetryReport, Integer> {

}
