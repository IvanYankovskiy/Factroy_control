package com.factory.control.service.report.extruder;

import com.factory.control.repository.ExtruderTelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExtruderTelemetryReportGenerator {

    private final ExtruderTelemetryRepository extruderTelemetryRepository;

    @Autowired
    public ExtruderTelemetryReportGenerator(ExtruderTelemetryRepository extruderTelemetryRepository) {
        this.extruderTelemetryRepository = extruderTelemetryRepository;
    }



}
