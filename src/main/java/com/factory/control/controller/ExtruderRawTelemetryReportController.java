package com.factory.control.controller;

import com.factory.control.controller.dto.ExtruderRawTelemetryReportDTO;
import com.factory.control.service.report.ExtruderRawTelemetryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

import static java.time.Duration.ofDays;
import static java.time.LocalDate.now;

@RestController
public class ExtruderRawTelemetryReportController {
    
    private final ExtruderRawTelemetryReportService service;

    @Autowired
    public ExtruderRawTelemetryReportController(ExtruderRawTelemetryReportService service) {
        this.service = service;
    }

    @GetMapping("extruder/{uuid}/report/raw/lasthour")
    public ExtruderRawTelemetryReportDTO getLastHourRawTelemetry(@PathVariable String uuid) {
        return service.getRawTelemetryReportForLastDuration(uuid, Duration.ofHours(1));
    }

    @GetMapping("extruder/{uuid}/report/raw/last12hours")
    public ExtruderRawTelemetryReportDTO getLast12HoursRawTelemetry(@PathVariable String uuid) {
        return service.getRawTelemetryReportForLastDuration(uuid, Duration.ofHours(12));
    }

    @GetMapping("extruder/{uuid}/report/raw/lastweek")
    public ExtruderRawTelemetryReportDTO getLastWeekRawTelemetry(@PathVariable String uuid) {
        return service.getRawTelemetryReportForLastDuration(uuid, ofDays(7));
    }

    @GetMapping("extruder/{uuid}/report/raw/lastmonth")
    public ExtruderRawTelemetryReportDTO getLastMonthRawTelemetry(@PathVariable String uuid) {
        LocalDate today = now();
        return service.getRawTelemetryReportForLastPeriod(uuid, Period.between(today.withDayOfMonth(1), today.plusDays(1)));
    }
    
}
