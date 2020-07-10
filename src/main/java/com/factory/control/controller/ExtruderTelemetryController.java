package com.factory.control.controller;

import com.factory.control.controller.dto.ExtruderTelemetryDTO;
import com.factory.control.service.ExtruderTelemetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ExtruderTelemetryController {

    private ExtruderTelemetryService extruderTelemetryService;

    @Autowired
    public ExtruderTelemetryController(ExtruderTelemetryService extruderTelemetryService) {
        this.extruderTelemetryService = extruderTelemetryService;
    }

    @PostMapping("/telemetry/extruder/{token}")
    public String saveTelemetry(@PathVariable String token, @Valid @RequestBody ExtruderTelemetryDTO extruderTelemetryDTO) {
        return extruderTelemetryService.saveTelemetry(token, extruderTelemetryDTO);
    }

}
