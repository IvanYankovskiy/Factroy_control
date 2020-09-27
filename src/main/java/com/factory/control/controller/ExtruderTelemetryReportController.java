package com.factory.control.controller;

import com.factory.control.controller.dto.report.extruder.ExtruderTelemetryReportDTO;
import com.factory.control.service.report.extruder.ExtruderTelemetryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExtruderTelemetryReportController {

    private final ExtruderTelemetryReportService service;

    @Autowired
    public ExtruderTelemetryReportController(ExtruderTelemetryReportService service) {
        this.service = service;
    }

    @GetMapping("extruder/{token}/report/lasthour")
    public ExtruderTelemetryReportDTO getLastHourPerformance(@PathVariable String token) {
        return service.getTelemetryReportForLastHour(token);
    }

    @GetMapping("extruder/{token}/report/last12hours")
    public ExtruderTelemetryReportDTO getLast12HoursPerformance(@PathVariable String token) {
        return service.getTelemetryReportForLast12Hours(token);
    }

    @GetMapping("extruder/{token}/report/lastweek")
    public ExtruderTelemetryReportDTO getLastWeekPerformance(@PathVariable String token) {
        return service.getTelemetryReportForLastWeek(token);
    }

    @GetMapping("extruder/{token}/report/lastmonth")
    public ExtruderTelemetryReportDTO getLastMonthPerformance(@PathVariable String token) {
        return service.getTelemetryReportForLastMonth(token);
    }

}
