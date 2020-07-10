package com.factory.control.controller;

import com.factory.control.controller.dto.DeviceDTO;
import com.factory.control.service.DeviceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DeviceController {

    final DeviceServiceImpl deviceService;

    @Autowired
    public DeviceController(DeviceServiceImpl deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/device")
    public List<DeviceDTO> selectAllDevices() {
        return deviceService.selectAll();
    }

    @GetMapping("/device/{token}")
    public DeviceDTO selectByToken(@PathVariable("token") String token) {
        return deviceService.selectByToken(token);
    }

    @PostMapping("/device")
    public DeviceDTO createDevice(@Valid @RequestBody DeviceDTO deviceDto) {
        return deviceService.createDevice(deviceDto);
    }

    @PostMapping("/device/{token}")
    public DeviceDTO updateDevice(@PathVariable("token") String token, @Valid @RequestBody DeviceDTO deviceDto) {
        return deviceService.updateDevice(token, deviceDto);
    }
}
