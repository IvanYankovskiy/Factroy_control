package com.factory.control.service.report;

import com.factory.control.domain.bo.ExtruderRawTelemetryReport;
import com.factory.control.domain.bo.InMemoryFileContainer;
import com.factory.control.domain.entities.ExtruderTelemetry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

@Service
public class ExtruderRawTelemetryExcelService {

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
        Map<String, Object> extruderDescription = createAnyDescription(rawTelemetryReport.getExtruder());
        int rowAfterDescription = addKeyValueRows(
                0,
                sheet,
                extruderDescription
        );

        Row row = sheet.createRow(rowAfterDescription);
        Cell cell = row.createCell(0);
        cell.setCellValue("Aggregated with");

        Map<String, Object> aggregationDescription = createAnyDescription(rawTelemetryReport.getAggregationSettings());
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
            Row row = sheet.createRow(startRowIndex++);
            createCell(0, row, entry.getKey());
            createCell(1, row, entry.getValue());
        }
        return currentRow;
    }

    private <T> void createCell(final int startColumn, final Row row, final T value) {
        Cell cell = row.createCell(startColumn);
        cell.setCellValue(String.valueOf(value));
    }

    protected <T> Map<String, Object> createAnyDescription(T instance) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(instance, Map.class);
    }

    private int addTelemetryRows(int startRowIndex, Sheet sheet, ExtruderRawTelemetryReport<OffsetDateTime> rawTelemetryReport) {
        int currentRowIndex = startRowIndex;
        Row headerRow = sheet.createRow(currentRowIndex);
        headerRow.createCell(0).setCellValue("time");
        headerRow.createCell(1).setCellValue("counter");
        headerRow.createCell(2).setCellValue("length(m)");
        currentRowIndex++;
        BigDecimal circumference = rawTelemetryReport.getExtruder().getCircumference();
        for (ExtruderTelemetry item : rawTelemetryReport.getTelemetry()) {
            Row row = sheet.createRow(currentRowIndex);
            row.createCell(0).setCellValue(item.getTime().toString());
            row.createCell(1).setCellValue(item.getCounter());
            row.createCell(2).setCellValue(
                    String.valueOf(
                            circumference.multiply(BigDecimal.valueOf(item.getCounter()))
                    )
            );
            currentRowIndex++;
        }
        return currentRowIndex;
    }
}
