package com.factory.control.service.report.extruder;

import com.factory.control.controller.dto.report.extruder.ExtruderTelemetryReportDTO;
import com.factory.control.controller.dto.report.extruder.ExtruderTelemetryReportTotalDTO;
import com.factory.control.domain.entities.ExtruderTelemetryReport;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.time.OffsetDateTime.of;
import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

class ExtruderFinalReportGeneratorTest {

    private ExtruderFinalReportGenerator generator = new ExtruderFinalReportGenerator();

    @Test
    void shouldAggregateReportsHourly() {
        // given
        List<ExtruderTelemetryReport> reports = createReports();

        // when
        ExtruderTelemetryReportTotalDTO actual = generator.generateReport(
                reports,
                of(2020, 10, 21, 23, 0, 0, 0, UTC),
                of(2020, 10, 22, 4, 0, 0, 0, UTC)
        );

        //then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(createExpectedReportDto());
    }

    private List<ExtruderTelemetryReport> createReports() {

        ExtruderTelemetryReport report1 = new ExtruderTelemetryReport();
        report1.setTime(of(2020, 10, 21, 23, 0, 0,0, UTC));
        report1.setLength(BigDecimal.valueOf(1.0));
        report1.setWeight(BigDecimal.valueOf(10.0));

        ExtruderTelemetryReport report2 = new ExtruderTelemetryReport();
        report2.setTime(of(2020, 10, 22, 1, 15, 38,0, UTC));
        report2.setLength(BigDecimal.valueOf(1.0));
        report2.setWeight(BigDecimal.valueOf(10.0));

        ExtruderTelemetryReport report3 = new ExtruderTelemetryReport();
        report3.setTime(of(2020, 10, 22, 1, 46, 14,0, UTC));
        report3.setLength(BigDecimal.valueOf(1.0));
        report3.setWeight(BigDecimal.valueOf(10.0));

        ExtruderTelemetryReport report4 = new ExtruderTelemetryReport();
        report4.setTime(of(2020, 10, 22, 3, 52, 16,0, UTC));
        report4.setLength(BigDecimal.valueOf(1.0));
        report4.setWeight(BigDecimal.valueOf(10.0));

        //should be excluded
        ExtruderTelemetryReport report5 = new ExtruderTelemetryReport();
        report5.setTime(of(2020, 10, 22, 4, 0, 0,0, UTC));
        report5.setLength(BigDecimal.valueOf(1.0));
        report5.setWeight(BigDecimal.valueOf(10.0));

        return newArrayList(report1, report2, report3, report4, report5);
    }

    private ExtruderTelemetryReportTotalDTO createExpectedReportDto() {
        ExtruderTelemetryReportDTO reportDto1 = new ExtruderTelemetryReportDTO();
        reportDto1.setStartOfPeriod(of(2020, 10, 21, 23, 0, 0,0, UTC));
        reportDto1.setEndOfPeriod(of(2020, 10, 22, 0, 0, 0,0, UTC));
        reportDto1.setLengthPerformance(BigDecimal.valueOf(1.0));
        reportDto1.setWeightPerformance(BigDecimal.valueOf(10.0));

        ExtruderTelemetryReportDTO reportDto2 = new ExtruderTelemetryReportDTO();
        reportDto2.setStartOfPeriod(of(2020, 10, 22, 0, 0, 0,0, UTC));
        reportDto2.setEndOfPeriod(of(2020, 10, 22, 1, 0, 0,0, UTC));
        reportDto2.setLengthPerformance(BigDecimal.valueOf(0.0));
        reportDto2.setWeightPerformance(BigDecimal.valueOf(0.0));

        ExtruderTelemetryReportDTO reportDto3 = new ExtruderTelemetryReportDTO();
        reportDto3.setStartOfPeriod(of(2020, 10, 22, 1, 0, 0,0, UTC));
        reportDto3.setEndOfPeriod(of(2020, 10, 22, 2, 0, 0,0, UTC));
        reportDto3.setLengthPerformance(BigDecimal.valueOf(2.0));
        reportDto3.setWeightPerformance(BigDecimal.valueOf(20.0));

        ExtruderTelemetryReportDTO reportDto4 = new ExtruderTelemetryReportDTO();
        reportDto4.setStartOfPeriod(of(2020, 10, 22, 2, 0, 0,0, UTC));
        reportDto4.setEndOfPeriod(of(2020, 10, 22, 3, 0, 0,0, UTC));
        reportDto4.setLengthPerformance(BigDecimal.valueOf(0.0));
        reportDto4.setWeightPerformance(BigDecimal.valueOf(0.0));

        ExtruderTelemetryReportDTO reportDto5 = new ExtruderTelemetryReportDTO();
        reportDto5.setStartOfPeriod(of(2020, 10, 22, 3, 0, 0,0, UTC));
        reportDto5.setEndOfPeriod(of(2020, 10, 22, 4, 0, 0,0, UTC));
        reportDto5.setLengthPerformance(BigDecimal.valueOf(1.0));
        reportDto5.setWeightPerformance(BigDecimal.valueOf(10.0));

        ExtruderTelemetryReportTotalDTO reportTotalDto = new ExtruderTelemetryReportTotalDTO();
        reportTotalDto.setTotalLength(BigDecimal.valueOf(4.0));
        reportTotalDto.setTotalWeight(BigDecimal.valueOf(40.0));
        reportTotalDto.setFrom(of(2020, 10, 21, 23, 0, 0,0, UTC));
        reportTotalDto.setTo(of(2020, 10, 22, 4, 0, 0,0, UTC));
        reportTotalDto.setDetails(newArrayList(reportDto1, reportDto2, reportDto3, reportDto4, reportDto5));
        return reportTotalDto;
    }

}
