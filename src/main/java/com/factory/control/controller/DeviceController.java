package com.factory.control.controller;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.service.DeviceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DeviceController {

    private Map<String, DeviceManagementService> servicesMap = new HashMap<>();

    @Autowired
    public DeviceController(List<DeviceManagementService> deviceService) {
        servicesMap = deviceService.stream()
                .collect(Collectors.toMap(DeviceManagementService::getDeviceType, deviceManagementService -> deviceManagementService));
    }

    @GetMapping("/device/{type}")
    public List<DeviceDTO> selectAllDevices(@PathVariable("type") String type) {
        DeviceManagementService deviceManagementService = getDeviceManagementService(type);
        return deviceManagementService.selectAll();
    }

    @GetMapping("/device/{type}/{token}")
    public DeviceDTO selectByToken(@PathVariable("type") String type,
                                   @PathVariable("token") String token) {
        DeviceManagementService deviceManagementService = getDeviceManagementService(type);
        return deviceManagementService.selectByToken(token);
    }

    @PostMapping("/device/{type}")
    public DeviceDTO createDevice(@PathVariable("type") String type,
                                  @Valid @RequestBody DeviceDTO deviceDto) {
        DeviceManagementService deviceManagementService = getDeviceManagementService(type);
        return deviceManagementService.createDevice(deviceDto);
    }

    @PostMapping("/device//{type}/{token}")
    public DeviceDTO updateDevice(@PathVariable("type") String type,
                                  @PathVariable("token") String token, @Valid @RequestBody DeviceDTO deviceDto) {
        DeviceManagementService deviceManagementService = getDeviceManagementService(type);
        return deviceManagementService.updateDevice(token, deviceDto);
    }

    private DeviceManagementService getDeviceManagementService(@PathVariable("type") String type) {
        DeviceManagementService deviceManagementService = servicesMap.get(type);
        if (deviceManagementService == null)
            throw new RuntimeException("no such type");
        return deviceManagementService;
    }
}
