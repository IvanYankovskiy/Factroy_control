package com.factory.control.service.report;

import com.factory.control.controller.dto.ExtruderDTO;
import com.factory.control.controller.dto.ExtruderRawTelemetryRecordDTO;
import com.factory.control.controller.dto.ExtruderRawTelemetryReportDTO;
import com.factory.control.controller.mapper.ExtruderMapper;
import com.factory.control.domain.entities.Extruder;
import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.repository.ExtruderRepository;
import com.factory.control.repository.ExtruderTelemetryRepository;
import com.factory.control.service.exception.DeviceIsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
public class ExtruderRawTelemetryReportService {

    private final ExtruderTelemetryRepository repository;

    private final ExtruderRepository extruderRepository;

    private final ExtruderMapper extruderMapper;

    @Autowired
    public ExtruderRawTelemetryReportService(ExtruderTelemetryRepository repository, ExtruderRepository extruderRepository,
                                             ExtruderMapper extruderMapper) {
        this.repository = repository;
        this.extruderRepository = extruderRepository;
        this.extruderMapper = extruderMapper;
    }

    public ExtruderRawTelemetryReportDTO getRawTelemetryReportForLastDuration(String uuid, Duration duration) {
        OffsetDateTime endOfPeriod = OffsetDateTime.now();
        OffsetDateTime startOfPeriod = endOfPeriod.minus(duration);
        return getRawTelemetryReportByPeriod(uuid, startOfPeriod, endOfPeriod);
    }

    public ExtruderRawTelemetryReportDTO getRawTelemetryReportForLastPeriod(String uuid, Period period) {
        OffsetDateTime endOfPeriod = OffsetDateTime.now();
        OffsetDateTime startOfPeriod = endOfPeriod.minus(period);
        return getRawTelemetryReportByPeriod(uuid, startOfPeriod, endOfPeriod);
    }

    protected ExtruderRawTelemetryReportDTO getRawTelemetryReportByPeriod(String uuid, OffsetDateTime startOfPeriod, OffsetDateTime endOfPeriod) {
        Extruder device = Optional.of(extruderRepository.findByUuid(uuid))
                .orElseThrow(() -> new DeviceIsNotFoundException(uuid));
        List<ExtruderTelemetry> telemetryList = repository
                .findTelemetriesInPeriod(device.getId(), startOfPeriod, endOfPeriod)
                .orElse(emptyList());

        ExtruderRawTelemetryReportDTO report = new ExtruderRawTelemetryReportDTO();
        ExtruderDTO extruderDTO = extruderMapper.fromEntityToDto(device);
        report.setDeviceDTO(extruderDTO);
        List<ExtruderRawTelemetryRecordDTO> telemetryRecords = telemetryList.stream()
                .map(ExtruderRawTelemetryRecordDTO::new)
                .collect(Collectors.toList());
        report.setRecords(telemetryRecords);
        return report;
    }
}
