package com.factory.control.repository;

import com.factory.control.domain.entities.ExtruderTelemetryReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

@Repository
public interface ExtruderTelemetryReportRepository extends JpaRepository<ExtruderTelemetryReport, Integer> {

    @Query("select etr from ExtruderTelemetryReport etr where etr.device.id = :device_id and etr.time >= :from and etr.time < :to order by etr.time")
    LinkedList<ExtruderTelemetryReport> findReportsInPeriod(
            @Param("device_id") Integer deviceId,
            @Param("from") OffsetDateTime from,
            @Param("to") OffsetDateTime to);

}
