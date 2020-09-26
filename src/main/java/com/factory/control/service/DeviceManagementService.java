package com.factory.control.service;

import com.factory.control.controller.dto.DeviceDTO;

import java.util.List;

public interface DeviceManagementService<T extends DeviceDTO> {

    T createDevice(T newDeviceDto);

    T updateDevice(String token, T deviceDto);

    List<T> selectAll();

    T selectByToken(String token);

    String getDeviceType();
}
