package com.factory.control.service;

import com.factory.control.controller.dto.ExtruderTelemetryDTO;
import com.factory.control.controller.mapper.ExtruderTelemetryMapper;
import com.factory.control.domain.Device;
import com.factory.control.domain.ExtruderTelemetry;
import com.factory.control.repository.DeviceRepository;
import com.factory.control.repository.ExtruderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Objects;

@Service
public class ExtruderTelemetryService {

    private ExtruderTelemetryMapper extruderTelemetryMapper;

    private ExtruderRepository repository;

    private DeviceRepository deviceRepository;

    @Autowired
    public ExtruderTelemetryService(ExtruderTelemetryMapper extruderTelemetryMapper, ExtruderRepository repository, DeviceRepository deviceRepository) {
        this.repository = repository;
        this.extruderTelemetryMapper = extruderTelemetryMapper;
        this.deviceRepository = deviceRepository;
    }

    public String saveTelemetry(String token, ExtruderTelemetryDTO telemetryDTO) {
        Device device = deviceRepository.findByToken(token);
        if (Objects.isNull(device)) {
            new RuntimeException("Device with token " + token + " doesn't exist");
        }
        ExtruderTelemetry extruderTelemetry = extruderTelemetryMapper.fromDtoToEntity(telemetryDTO);
        extruderTelemetry.setDeviceId(device);
        extruderTelemetry.setTime(OffsetDateTime.now());
        repository.saveAndFlush(extruderTelemetry);
        return "ok";
    }
}
