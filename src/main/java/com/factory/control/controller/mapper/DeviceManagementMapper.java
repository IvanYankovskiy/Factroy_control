package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.domain.entities.device.Device;

import java.util.Collection;
import java.util.List;

public interface DeviceManagementMapper<D extends DeviceDTO, E extends Device> {

    E fromDtoToEntity(D dto);

    D fromEntityToDto(E entity);

    List<D> fromEntitiesToDTOs(Collection<E> entities);

}
