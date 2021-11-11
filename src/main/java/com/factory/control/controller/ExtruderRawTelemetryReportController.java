package com.factory.control.controller;

import com.factory.control.controller.dto.AggregationSettingsDTO;
import com.factory.control.controller.dto.ExtruderRawTelemetryAggregatedReportDTO;
import com.factory.control.controller.dto.ExtruderRawTelemetryReportDTO;
import com.factory.control.domain.bo.InMemoryFileContainer;
import com.factory.control.facade.ExtruderTelemetryFacade;
import com.factory.control.service.report.ExtruderRawTelemetryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;

import static java.time.Duration.ofDays;
import static java.time.LocalDate.now;

@RestController
public class ExtruderRawTelemetryReportController {
    private final ExtruderRawTelemetryReportService service;
    private final ExtruderTelemetryFacade extruderTelemetryFacade;

    @Autowired
    public ExtruderRawTelemetryReportController(
            ExtruderRawTelemetryReportService service,
            ExtruderTelemetryFacade extruderTelemetryFacade
    ) {
        this.service = service;
        this.extruderTelemetryFacade = extruderTelemetryFacade;
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

    @GetMapping("extruder/{uuid}/report/raw")
    public ExtruderRawTelemetryAggregatedReportDTO<OffsetDateTime> getWithAggregation(
            @PathVariable String uuid,
            AggregationSettingsDTO settings
    ) {
        return extruderTelemetryFacade.aggregateForSingleDevice(uuid, settings);
    }

    @GetMapping("extruder/{uuid}/report/raw/excel")
    public ResponseEntity<byte[]> getWithAggregationAsExcel(
            @PathVariable String uuid,
            AggregationSettingsDTO settings
    ) throws IOException {
        InMemoryFileContainer report = extruderTelemetryFacade.aggregateForSingleDeviceAsExcel(uuid, settings);
        HttpHeaders headers = getHttpHeadersForExcelReport(report);
        return new ResponseEntity<>(report.getContent(), headers, HttpStatus.OK);
    }

    private HttpHeaders getHttpHeadersForExcelReport(InMemoryFileContainer report) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=" + report.getFullName());
        headers.set("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        return headers;
    }

}
