package com.factory.control.service.report;

import com.factory.control.controller.dto.ExtruderTelemetryReportDTO;
import com.factory.control.controller.dto.ExtruderTelemetryReportTotalDTO;
import com.factory.control.domain.bo.InMemoryFileContainer;
import com.factory.control.service.exception.ReportCreationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Duration;
import java.time.Period;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExtruderCsvTelemetryReportService {
    private static final String LAST_HOUR_LABEL = "last-hour";
    private static final String LAST_12_HOURS_LABEL = "last-12-hours";
    private static final String LAST_WEEK_LABEL = "last-week";
    private static final String LAST_MONTH_LABEL = "last-month";

    private final ExtruderTelemetryReportService reportService;

    public InMemoryFileContainer getCsvReportForLastDuration(String uuid, Duration duration) {
        ExtruderTelemetryReportTotalDTO report = reportService.getTelemetryReportForLastDuration(uuid, duration);
        return generateCsvForReport(report);
    }

    public InMemoryFileContainer getCsvReportForLastPeriod(String uuid, Period period) {
        ExtruderTelemetryReportTotalDTO report = reportService.getTelemetryReportForLastPeriod(uuid, period);
        return generateCsvForReport(report);
    }

    private InMemoryFileContainer generateCsvForReport(ExtruderTelemetryReportTotalDTO report) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream);
        try {
            CSVPrinter printer = new CSVPrinter(outputStreamWriter, CSVFormat.EXCEL);
            printer.printRecord("Device name", report.getExtruderName());
            printer.printRecord("From", report.getFrom());
            printer.printRecord("To", report.getTo());
            printer.printRecord("Total weight (kg)", report.getTotalWeight());
            printer.printRecord("Total length (m)", report.getTotalLength());
            printer.printRecord("Details");
            printer.printRecord("Period", "Weight (kg)", "Length (m)");
            for (ExtruderTelemetryReportDTO detail : report.getDetails()) {
                String subPeriod = detail.getStartOfPeriod().toString() + " - " + detail.getEndOfPeriod().toString();
                printer.printRecord(subPeriod, detail.getWeightPerformance(), detail.getLengthPerformance());
            }
            printer.flush();
        } catch (IOException e) {
            log.error("Cannot generate csv report");
            throw new ReportCreationException();
        }
        return new InMemoryFileContainer(
                generateName(report),
                "csv",
                byteArrayOutputStream.toByteArray()
        );
    }

    private String generateName(ExtruderTelemetryReportTotalDTO report) {
        return report.getExtruderName() + "-lastHour";
    }
}
