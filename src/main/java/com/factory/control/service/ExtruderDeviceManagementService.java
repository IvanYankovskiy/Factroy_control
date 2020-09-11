package com.factory.control.service;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.controller.dto.ExtruderDTO;
import com.factory.control.controller.mapper.ExtruderMapper;
import com.factory.control.domain.entities.device.DeviceType;
import com.factory.control.domain.entities.device.Extruder;
import com.factory.control.service.exception.DeviceIsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExtruderDeviceManagementService implements DeviceManagementService {

    private final DeviceCrudServiceAbstract<Extruder, Integer> crudService;

    private final ExtruderMapper extruderMapper;

    @Autowired
    public ExtruderDeviceManagementService(DeviceCrudServiceAbstract extruderCrudService,
                                           ExtruderMapper extruderMapper) {
        this.crudService = extruderCrudService;
        this.extruderMapper = extruderMapper;
    }

    @Override
    public DeviceDTO createDevice(DeviceDTO newDeviceDto) {
        Extruder device = extruderMapper.fromDtoToEntity((ExtruderDTO) newDeviceDto);
        crudService.create(device);
        return extruderMapper.fromEntityToDto(device);
    }

    @Override
    public DeviceDTO updateDevice(String token, DeviceDTO deviceDto) {
        Extruder persisted = Optional.of(crudService.selectByToken(token))
                .orElseThrow(() -> new DeviceIsNotFoundException(token));
        Extruder deviceWish = extruderMapper.fromDtoToEntity((ExtruderDTO) deviceDto);
        crudService.update(persisted, deviceWish);
        return extruderMapper.fromEntityToDto(persisted);
    }

    @Override
    public List<DeviceDTO> selectAll() {
        List<Extruder> allDevices = crudService.selectAll();
        return (List<DeviceDTO>) extruderMapper.fromEntitiesToDTOs(allDevices);
    }

    @Override
    public ExtruderDTO selectByToken(String token) {
        Extruder persisted = Optional.of(crudService.selectByToken(token))
                .orElseThrow(() -> new DeviceIsNotFoundException(token));
        return extruderMapper.fromEntityToDto(persisted);
    }

    @Override
    public String getDeviceType() {
        return DeviceType.EXTRUDER.name();
    }
}
