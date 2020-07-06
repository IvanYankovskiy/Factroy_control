package com.factory.control.controller;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeviceController {

    final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/device")
    public DeviceDTO createDevice(@RequestBody DeviceDTO deviceDto) {
        return deviceService.createDevice(deviceDto);
    }

}
