package com.factory.control.controller;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.domain.entities.DeviceType;
import com.factory.control.service.DeviceManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class DeviceController<T extends DeviceDTO> {

    private Map<String, DeviceManagementService<T>> servicesMap;

    @Autowired
    public DeviceController(List<DeviceManagementService<T>> deviceService) {
        servicesMap = deviceService.stream()
                .collect(Collectors.toMap(DeviceManagementService::getDeviceType, deviceManagementService -> deviceManagementService));
    }

    @GetMapping("/dictionary/device/types")
    public List<String> getDeviceTypes() {
        return Arrays.stream(DeviceType.values())
                .map(DeviceType::name)
                .collect(Collectors.toList());
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

    @GetMapping("/device/{type}/{uuid}")
    public T selectByUuid(@PathVariable("type") String type,
                          @PathVariable("uuid") String uuid) {
        DeviceManagementService<T> deviceManagementService = getDeviceManagementService(type);
        return deviceManagementService.selectByUuid(uuid);
    }

    @PostMapping("/device/{type}")
    public T createDevice(@PathVariable("type") String type,
                                  @Valid @RequestBody T deviceDto) {
        DeviceManagementService<T> deviceManagementService = getDeviceManagementService(type);
        return deviceManagementService.createDevice(deviceDto);
    }

    @PutMapping("/device/{type}/{uuid}")
    public T updateDevice(@PathVariable("type") String type,
                                  @PathVariable("uuid") String uuid, @Valid @RequestBody T deviceDto) {
        DeviceManagementService<T> deviceManagementService = getDeviceManagementService(type);
        return deviceManagementService.updateDevice(uuid, deviceDto);
    }

    private DeviceManagementService<T> getDeviceManagementService(String type) {
        DeviceManagementService<T> deviceManagementService = servicesMap.get(type);
        if (deviceManagementService == null)
            throw new RuntimeException("no such type");
        return deviceManagementService;
    }
}
