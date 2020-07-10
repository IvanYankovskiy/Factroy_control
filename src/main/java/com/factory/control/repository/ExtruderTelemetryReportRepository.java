package com.factory.control.repository;

import com.factory.control.domain.Device;
import com.factory.control.domain.ExtruderTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface ExtruderTelemetryReportRepository extends JpaRepository<ExtruderTelemetry, Integer> {

    List<ExtruderTelemetry> findExtruderTelemetriesByDeviceIdIsAndTimeAfterAndTimeBeforeOrderByTime(Device device, OffsetDateTime startDateTime, OffsetDateTime endDateTime);

}
