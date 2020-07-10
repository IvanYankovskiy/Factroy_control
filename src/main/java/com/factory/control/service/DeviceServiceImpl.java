package com.factory.control.service;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.controller.mapper.DeviceMapper;
import com.factory.control.domain.Device;
import com.factory.control.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    private final DeviceMapper deviceMapper;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    public DeviceDTO createDevice(DeviceDTO newDeviceDto) {
        Device device = deviceMapper.fromDtoToEntity(newDeviceDto);
        device.setToken(UUID.randomUUID().toString());
        deviceRepository.saveAndFlush(device);
        return deviceMapper.fromEntityToDto(device);
    }

    public DeviceDTO updateDevice(String token, DeviceDTO deviceDto) {
        Device persisted = deviceRepository.findByToken(token);
        if (Objects.isNull(persisted)) {
            throw new RuntimeException("There is now device with token " + token);
        }
        Device deviceWish = deviceMapper.fromDtoToEntity(deviceDto);
        persisted.setName(deviceWish.getName());
        persisted.setDescription(deviceWish.getDescription());
        deviceRepository.saveAndFlush(persisted);
        return deviceMapper.fromEntityToDto(persisted);
    }

    public List<DeviceDTO> selectAll() {
        List<Device> allDevices = deviceRepository.findAll();
        return deviceMapper.fromEntitiesToDTOs(allDevices);
    }

    public DeviceDTO selectByToken(String token) {
        Device device = deviceRepository.findByToken(token);
        if (Objects.isNull(device)) {
            throw new RuntimeException("There is now device with token " + token);
        }
        return deviceMapper.fromEntityToDto(device);
    }
}
