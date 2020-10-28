package com.factory.control.repository;

import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.domain.entities.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExtruderTelemetryReportRepository extends JpaRepository<ExtruderTelemetry, Integer> {

    Optional<List<ExtruderTelemetry>> findExtruderTelemetriesByDeviceIdIsAndTimeAfterAndTimeBeforeOrderByTime(Device device, OffsetDateTime startDateTime, OffsetDateTime endDateTime);

    @Query("select distinct(et.deviceId.id) from ExtruderTelemetry et")
    List<Integer> selectDistictDevicesForPeriod(OffsetDateTime from, OffsetDateTime to);

}
