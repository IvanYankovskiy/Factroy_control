package com.factory.control.controller;

import com.factory.control.controller.dto.ExtruderTelemetryReportTotalDTO;
import com.factory.control.domain.bo.InMemoryFileContainer;
import com.factory.control.service.report.ExtruderCsvTelemetryReportService;
import com.factory.control.service.report.ExtruderTelemetryReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;

import static java.time.Duration.ofDays;
import static java.time.Duration.ofHours;
import static java.time.LocalDate.now;

@RestController
@RequiredArgsConstructor
public class ExtruderTelemetryReportController {

    private final ExtruderTelemetryReportService service;
    private final ExtruderCsvTelemetryReportService extruderCsvTelemetryReportService;

    @GetMapping("extruder/{uuid}/report/lasthour")
    public ExtruderTelemetryReportTotalDTO getLastHourPerformance(@PathVariable String uuid) {
        return service.getTelemetryReportForLastDuration(uuid, ofHours(1));
    }

    @GetMapping("extruder/{uuid}/report/last12hours")
    public ExtruderTelemetryReportTotalDTO getLast12HoursPerformance(@PathVariable String uuid) {
        return service.getTelemetryReportForLastDuration(uuid, ofHours(12));
    }

    @GetMapping("extruder/{uuid}/report/lastweek")
    public ExtruderTelemetryReportTotalDTO getLastWeekPerformance(@PathVariable String uuid) {
        return service.getTelemetryReportForLastDuration(uuid, ofDays(7));
    }

    @GetMapping("extruder/{uuid}/report/lastmonth")
    public ExtruderTelemetryReportTotalDTO getLastMonthPerformance(@PathVariable String uuid) {
        LocalDate today = now();
        return service.getTelemetryReportForLastPeriod(uuid, Period.between(today.withDayOfMonth(1), today.plusDays(1)));
    }

    @GetMapping("extruder/{uuid}/report/csv/last-hour")
    public ResponseEntity<byte[]> getLastHourPerformanceAsCsv(@PathVariable String uuid) {
        InMemoryFileContainer report = extruderCsvTelemetryReportService.getCsvReportForLastDuration(uuid, ofHours(1));
        HttpHeaders headers = getHttpHeadersForCsvReport(report);
        return new ResponseEntity<>(report.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("extruder/{uuid}/report/csv/last-12-hours")
    public ResponseEntity<byte[]> getLast12hoursPerformanceAsCsv(@PathVariable String uuid) {
        InMemoryFileContainer report = extruderCsvTelemetryReportService.getCsvReportForLastDuration(uuid, ofHours(12));
        HttpHeaders headers = getHttpHeadersForCsvReport(report);
        return new ResponseEntity<>(report.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("extruder/{uuid}/report/csv/last-week")
    public ResponseEntity<byte[]> getLastWeekPerformanceAsCsv(@PathVariable String uuid) {
        InMemoryFileContainer report = extruderCsvTelemetryReportService.getCsvReportForLastDuration(uuid, ofDays(7));
        HttpHeaders headers = getHttpHeadersForCsvReport(report);
        return new ResponseEntity<>(report.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("extruder/{uuid}/report/csv/last-month")
    public ResponseEntity<byte[]> getLastMonthPerformanceAsCsv(@PathVariable String uuid) {
        LocalDate today = now();
        InMemoryFileContainer report = extruderCsvTelemetryReportService.getCsvReportForLastPeriod(uuid, Period.between(today.withDayOfMonth(1), today.plusDays(1)));
        HttpHeaders headers = getHttpHeadersForCsvReport(report);
        return new ResponseEntity<>(report.getContent(), headers, HttpStatus.OK);
    }

    private HttpHeaders getHttpHeadersForCsvReport(InMemoryFileContainer report) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=" + report.getFullName());
        headers.set("Content-Type", "text/csv");
        return headers;
    }

}
