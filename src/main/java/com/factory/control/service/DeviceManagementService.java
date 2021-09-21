package com.factory.control.service;

import com.factory.control.controller.dto.DeviceDTO;

import java.util.List;

public interface DeviceManagementService<T extends DeviceDTO> {

    T createDevice(T newDeviceDto);

    T updateDevice(String uuid, T deviceDto);

    List<T> selectAll();

    T selectByUuid(String uuid);

    String getDeviceType();
}
