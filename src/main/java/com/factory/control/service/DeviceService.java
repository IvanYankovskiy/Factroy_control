package com.factory.control.service;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.controller.mapper.DeviceMapper;
import com.factory.control.domain.Device;
import com.factory.control.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    private final DeviceMapper deviceMapper;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    public DeviceDTO createDevice(DeviceDTO newDeviceDto) {
        Device device = deviceMapper.fromDtoToEntity(newDeviceDto);
        device.setToken(UUID.randomUUID().toString());
        deviceRepository.saveAndFlush(device);
        return deviceMapper.fromEntityToDto(device);
    }

}
