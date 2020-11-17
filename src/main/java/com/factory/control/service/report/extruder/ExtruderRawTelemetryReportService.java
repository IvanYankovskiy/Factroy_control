package com.factory.control.service.report.extruder;

import com.factory.control.controller.dto.ExtruderDTO;
import com.factory.control.controller.dto.report.extruder.ExtruderRawTelemetryRecordDTO;
import com.factory.control.controller.dto.report.extruder.ExtruderRawTelemetryReportDTO;
import com.factory.control.controller.mapper.ExtruderMapper;
import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.domain.entities.device.Extruder;
import com.factory.control.repository.ExtruderTelemetryRepository;
import com.factory.control.repository.device.ExtruderRepository;
import com.factory.control.service.exception.DeviceIsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ExtruderRawTelemetryReportDTO getRawTelemetryReportForLastDuration(String token, Duration duration) {
        OffsetDateTime endOfPeriod = OffsetDateTime.now();
        OffsetDateTime startOfPeriod = endOfPeriod.minus(duration);
        return getRawTelemetryReportByPeriod(token, startOfPeriod, endOfPeriod);
    }

    protected ExtruderRawTelemetryReportDTO getRawTelemetryReportByPeriod(String token, OffsetDateTime startOfPeriod, OffsetDateTime endOfPeriod) {
        Extruder device = Optional.of(extruderRepository.findByToken(token))
                .orElseThrow(() -> new DeviceIsNotFoundException(token));
        List<ExtruderTelemetry> telemetryList = repository
                .findTelemetriesInPeriod(device.getId(), startOfPeriod, endOfPeriod)
                .orElse(new ArrayList<>());

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
