package com.factory.control.controller;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.service.DeviceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DeviceController<T extends DeviceDTO> {

    private Map<String, DeviceManagementService<T>> servicesMap;

    @Autowired
    public DeviceController(List<DeviceManagementService> deviceService) {
        servicesMap = deviceService.stream()
                .collect(Collectors.toMap(DeviceManagementService::getDeviceType, deviceManagementService -> deviceManagementService));
    }

    @GetMapping("/device")
    public List<T> selectAllDevices() {
        List<T> allDevices = new ArrayList<>();
        servicesMap.values().forEach(s -> {
            allDevices.addAll(s.selectAll());
        });
        return allDevices;
    }

    @GetMapping("/device/{type}")
    public List<T> selectAllDevicesByType(@PathVariable("type") String type) {
        DeviceManagementService<T> deviceManagementService = getDeviceManagementService(type);
        return deviceManagementService.selectAll();
    }

    @GetMapping("/device/{type}/{token}")
    public T selectByToken(@PathVariable("type") String type,
                                   @PathVariable("token") String token) {
        DeviceManagementService<T> deviceManagementService = getDeviceManagementService(type);
        return deviceManagementService.selectByToken(token);
    }

    @PostMapping("/device/{type}")
    public T createDevice(@PathVariable("type") String type,
                                  @Valid @RequestBody T deviceDto) {
        DeviceManagementService<T> deviceManagementService = getDeviceManagementService(type);
        return deviceManagementService.createDevice(deviceDto);
    }

    @PostMapping("/device/{type}/{token}")
    public T updateDevice(@PathVariable("type") String type,
                                  @PathVariable("token") String token, @Valid @RequestBody T deviceDto) {
        DeviceManagementService<T> deviceManagementService = getDeviceManagementService(type);
        return deviceManagementService.updateDevice(token, deviceDto);
    }

    private DeviceManagementService<T> getDeviceManagementService(String type) {
        DeviceManagementService<T> deviceManagementService = servicesMap.get(type);
        if (deviceManagementService == null)
            throw new RuntimeException("no such type");
        return deviceManagementService;
    }
}
