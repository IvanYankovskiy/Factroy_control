package com.factory.control.service.telemetry;

import com.factory.control.controller.dto.ExtruderTelemetryDTO;
import com.factory.control.controller.mapper.ExtruderTelemetryMapper;
import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.domain.entities.device.Device;
import com.factory.control.repository.ExtruderTelemetryRepository;
import com.factory.control.repository.device.DeviceBaseRepository;
import com.factory.control.service.exception.DeviceIsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Objects;

@Service
public class ExtruderTelemetryService {

    private final ExtruderTelemetryMapper extruderTelemetryMapper;

    private final ExtruderTelemetryRepository repository;

    private final DeviceBaseRepository deviceRepository;

    @Autowired
    public ExtruderTelemetryService(ExtruderTelemetryMapper extruderTelemetryMapper, ExtruderTelemetryRepository repository, DeviceBaseRepository deviceRepository) {
        this.repository = repository;
        this.extruderTelemetryMapper = extruderTelemetryMapper;
        this.deviceRepository = deviceRepository;
    }

    public String saveTelemetry(String token, ExtruderTelemetryDTO telemetryDTO) {
        Device device = deviceRepository.findByToken(token);
        if (Objects.isNull(device)) {
            throw new DeviceIsNotFoundException(token);
        }
        ExtruderTelemetry extruderTelemetry = extruderTelemetryMapper.fromDtoToEntity(telemetryDTO);
        extruderTelemetry.setDeviceId(device);
        extruderTelemetry.setTime(OffsetDateTime.now());
        repository.saveAndFlush(extruderTelemetry);
        return "ok";
    }
}
