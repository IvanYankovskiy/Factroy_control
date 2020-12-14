package com.factory.control.controller.report.extruder;

import com.factory.control.controller.dto.report.extruder.ExtruderTelemetryReportTotalDTO;
import com.factory.control.service.report.extruder.ExtruderTelemetryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDate;

import static java.time.Duration.between;
import static java.time.Duration.ofHours;
import static java.time.LocalDate.now;

@RestController
public class ExtruderTelemetryReportController {

    private final ExtruderTelemetryReportService service;

    @Autowired
    public ExtruderTelemetryReportController(ExtruderTelemetryReportService service) {
        this.service = service;
    }

    @GetMapping("extruder/{token}/report/lasthour")
    public ExtruderTelemetryReportTotalDTO getLastHourPerformance(@PathVariable String token) {
        return service.getTelemetryReportForLastDuration(token, ofHours(1));
    }

    @GetMapping("extruder/{token}/report/last12hours")
    public ExtruderTelemetryReportTotalDTO getLast12HoursPerformance(@PathVariable String token) {
        return service.getTelemetryReportForLastDuration(token, ofHours(12));
    }

    @GetMapping("extruder/{token}/report/lastweek")
    public ExtruderTelemetryReportTotalDTO getLastWeekPerformance(@PathVariable String token) {
        return service.getTelemetryReportForLastDuration(token, Duration.ofDays(7));
    }

    @GetMapping("extruder/{token}/report/lastmonth")
    public ExtruderTelemetryReportTotalDTO getLastMonthPerformance(@PathVariable String token) {
        LocalDate today = now();
        return service.getTelemetryReportForLastDuration(token, between(today.withDayOfMonth(1), today.plusDays(1)));
    }

}
