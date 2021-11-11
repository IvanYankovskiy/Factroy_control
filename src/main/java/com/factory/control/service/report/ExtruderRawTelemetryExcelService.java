package com.factory.control.service.report;

import com.factory.control.domain.bo.ExtruderRawTelemetryReport;
import com.factory.control.domain.bo.InMemoryFileContainer;
import com.factory.control.domain.entities.ExtruderTelemetry;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExtruderRawTelemetryExcelService {
    private final ObjectMapper objectMapper;

    public InMemoryFileContainer convertToExcel(ExtruderRawTelemetryReport<OffsetDateTime> rawTelemetryReport) throws IOException {
        return new InMemoryFileContainer(
                rawTelemetryReport.getExtruder().getName(),
                "xlsx",
                convertToExcelBytes(rawTelemetryReport)
        );
    }

    private byte[] convertToExcelBytes(ExtruderRawTelemetryReport<OffsetDateTime> rawTelemetryReport) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Map<String, Object> extruderDescription = objectMapper.convertValue(rawTelemetryReport.getExtruder(), Map.class);
        int rowAfterDescription = addKeyValueRows(
                0,
                sheet,
                extruderDescription
        );

        Row row = sheet.createRow(rowAfterDescription);
        Cell cell = row.createCell(0);
        cell.setCellValue("Aggregated with");

        Map<String, Object> aggregationDescription = objectMapper.convertValue(rawTelemetryReport.getAggregationSettings(), Map.class);
        int rowAfterSettings = addKeyValueRows(
                rowAfterDescription + 1,
                sheet,
                aggregationDescription
        );

        addTelemetryRows(rowAfterSettings, sheet, rawTelemetryReport);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } catch (IOException e) {
            throw new RuntimeException("Unable to prepare telemetry report");
        } finally {
            workbook.close();
            bos.close();
        }
        return bos.toByteArray();
    }

    /**
     *
     * @param startRowIndex
     * @param sheet
     * @param keyValueRows
     * @return the next row index after created description
     */
    protected int addKeyValueRows(int startRowIndex, Sheet sheet, Map<String, Object> keyValueRows) {
        int currentRow = startRowIndex;
        for (Map.Entry<String, Object> entry : keyValueRows.entrySet()) {
            Row row = sheet.createRow(currentRow++);
            createCell(0, row, entry.getKey());
            createCell(1, row, entry.getValue());
        }
        return currentRow;
    }

    private <T> void createCell(final int startColumn, final Row row, final T value) {
        Cell cell = row.createCell(startColumn);
        cell.setCellValue(String.valueOf(value));
    }

    private int addTelemetryRows(int startRowIndex, Sheet sheet, ExtruderRawTelemetryReport<OffsetDateTime> rawTelemetryReport) {
        int currentRowIndex = startRowIndex;
        Row headerRow = sheet.createRow(currentRowIndex);
        createTelemetryHeadersRow(headerRow);
        currentRowIndex++;
        BigDecimal circumference = rawTelemetryReport.getExtruder().getCircumference();
        long counterSum = 0;
        BigDecimal lengthSum = BigDecimal.ZERO;
        BigDecimal toMeters = BigDecimal.valueOf(1000.0);
        for (ExtruderTelemetry item : rawTelemetryReport.getTelemetry()) {
            Row row = sheet.createRow(currentRowIndex);
            row.createCell(0).setCellValue(item.getTime().toString());
            row.createCell(1).setCellValue(item.getCounter());
            BigDecimal length = circumference.multiply(BigDecimal.valueOf(item.getCounter())).divide(toMeters, 6, RoundingMode.DOWN);
            row.createCell(2).setCellValue(String.valueOf(length));
            counterSum += item.getCounter();
            lengthSum = lengthSum.add(length);
            currentRowIndex++;
        }
        Row totalsRow = sheet.createRow(currentRowIndex);
        totalsRow.createCell(0).setCellValue("Totals");
        totalsRow.createCell(1).setCellValue(counterSum);
        totalsRow.createCell(2).setCellValue(String.valueOf(lengthSum));
        return currentRowIndex;
    }

    private void createTelemetryHeadersRow(Row headerRow) {
        headerRow.createCell(0).setCellValue("time");
        headerRow.createCell(1).setCellValue("counter");
        headerRow.createCell(2).setCellValue("length(m)");
    }
}
