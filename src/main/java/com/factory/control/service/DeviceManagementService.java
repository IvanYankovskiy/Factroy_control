package com.factory.control.service;

import com.factory.control.controller.dto.DeviceDTO;

import java.util.List;

public interface DeviceManagementService {

    DeviceDTO createDevice(DeviceDTO newDeviceDto);

    DeviceDTO updateDevice(String token, DeviceDTO deviceDto);

    List<DeviceDTO> selectAll();

    DeviceDTO selectByToken(String token);

    String getDeviceType();
}
