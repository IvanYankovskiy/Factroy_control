package com.factory.control.service;

import com.factory.control.controller.dto.ExtruderDTO;
import com.factory.control.controller.mapper.ExtruderMapper;
import com.factory.control.domain.entities.DeviceType;
import com.factory.control.domain.entities.Extruder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExtruderDeviceManagementService implements DeviceManagementService<ExtruderDTO> {

    private final DeviceCrudServiceAbstract<Extruder, Integer> crudService;

    private final ExtruderMapper extruderMapper;

    @Autowired
    public ExtruderDeviceManagementService(DeviceCrudServiceAbstract<Extruder, Integer> extruderCrudService,
                                           ExtruderMapper extruderMapper) {
        this.crudService = extruderCrudService;
        this.extruderMapper = extruderMapper;
    }

    @Override
    public ExtruderDTO createDevice(ExtruderDTO newDeviceDto) {
        Extruder device = extruderMapper.fromDtoToEntity(newDeviceDto);
        crudService.create(device);
        return extruderMapper.fromEntityToDto(device);
    }

    @Override
    public ExtruderDTO updateDevice(String uuid, ExtruderDTO deviceDto) {
        Extruder persisted = crudService.selectByUuid(uuid);
        Extruder deviceWish = extruderMapper.fromDtoToEntity(deviceDto);
        crudService.update(persisted, deviceWish);
        return extruderMapper.fromEntityToDto(persisted);
    }

    @Override
    public List<ExtruderDTO> selectAll() {
        List<Extruder> allDevices = crudService.selectAll();
        return extruderMapper.fromEntitiesToDTOs(allDevices);
    }

    @Override
    public ExtruderDTO selectByUuid(String uuid) {
        Extruder persisted = crudService.selectByUuid(uuid);
        return extruderMapper.fromEntityToDto(persisted);
    }

    @Override
    public String getDeviceType() {
        return DeviceType.EXTRUDER.name();
    }
}
