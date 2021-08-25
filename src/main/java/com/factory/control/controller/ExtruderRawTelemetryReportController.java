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

    @GetMapping("extruder/{token}/report/raw/lasthour")
    public ExtruderRawTelemetryReportDTO getLastHourRawTelemetry(@PathVariable String token) {
        return service.getRawTelemetryReportForLastDuration(token, Duration.ofHours(1));
    }

    @GetMapping("extruder/{token}/report/raw/last12hours")
    public ExtruderRawTelemetryReportDTO getLast12HoursRawTelemetry(@PathVariable String token) {
        return service.getRawTelemetryReportForLastDuration(token, Duration.ofHours(12));
    }

    @GetMapping("extruder/{token}/report/raw/lastweek")
    public ExtruderRawTelemetryReportDTO getLastWeekRawTelemetry(@PathVariable String token) {
        return service.getRawTelemetryReportForLastDuration(token, ofDays(7));
    }

    @GetMapping("extruder/{token}/report/raw/lastmonth")
    public ExtruderRawTelemetryReportDTO getLastMonthRawTelemetry(@PathVariable String token) {
        LocalDate today = now();
        return service.getRawTelemetryReportForLastPeriod(token, Period.between(today.withDayOfMonth(1), today.plusDays(1)));
    }
    
}
