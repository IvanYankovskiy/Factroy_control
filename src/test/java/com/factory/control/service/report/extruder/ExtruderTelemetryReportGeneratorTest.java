package com.factory.control.service.report.extruder;

import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.domain.entities.ExtruderTelemetryReport;
import com.factory.control.domain.entities.device.Extruder;
import com.factory.control.repository.ExtruderTelemetryReportRepository;
import com.factory.control.repository.ExtruderTelemetryRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.OffsetDateTime.of;
import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExtruderTelemetryReportGeneratorTest {

    @InjectMocks
    private ExtruderTelemetryReportGenerator generator;

    @Mock
    private ExtruderTelemetryRepository telemetryRepository;

    @Mock
    private ExtruderReportMetricsCalculator calculator;

    @Mock
    private ExtruderTelemetryReportRepository telemetryReportRepository;

    @Test
    @DisplayName("Should load all raw telemetry, then compute reports divided hourly, save it, then delete raw")
    void shouldCreateReportsThenSaveItThenDeleteRawTelemetry() {
        // given
        OffsetDateTime from = of(2020, 10, 21, 23, 0, 0,0, UTC);
        OffsetDateTime to = of(2020, 10, 22, 23, 0, 0,0, UTC);
        Extruder device = new Extruder();
        device.setId(1);
        device.setCircumference(BigDecimal.valueOf(100.00));
        List<ExtruderTelemetry> rawTelemetry = createRawTelemetry();
        given(telemetryRepository.findTelemetriesInPeriod(device.getId(), from, to))
                .willReturn(Optional.of(rawTelemetry));
        given(calculator.computeReportMetricsHourly(anyList(), eq(device))).willReturn(new ExtruderTelemetryReport());
        given(telemetryReportRepository.saveAll(anyList())).willAnswer(invocation -> invocation.getArgument(0, List.class));

        // when
        List<ExtruderTelemetryReport> actual = generator.generateReportRecordsForDevice(device, from, to);

        // then
        assertThat(actual.size()).isEqualTo(4);
        verify(calculator, times(4)).computeReportMetricsHourly(anyList(), eq(device));
        verify(telemetryReportRepository).saveAll(anyList());
        verify(telemetryRepository).deleteAll(rawTelemetry);
    }

    private List<ExtruderTelemetry> createRawTelemetry() {
        ExtruderTelemetry tm1 = new ExtruderTelemetry()
                .setTime(of(2020, 10, 22, 2, 15, 45,0, UTC));
        ExtruderTelemetry tm2 = new ExtruderTelemetry()
                .setTime(of(2020, 10, 22, 2, 16, 7,0, UTC));
        ExtruderTelemetry tm3 = new ExtruderTelemetry()
                .setTime(of(2020, 10, 22, 8, 1, 27,0, UTC));
        ExtruderTelemetry tm4 = new ExtruderTelemetry()
                .setTime(of(2020, 10, 22, 8, 1, 27,0, UTC));
        ExtruderTelemetry tm5 = new ExtruderTelemetry()
                .setTime(of(2020, 10, 22, 11, 46, 5,0, UTC));
        ExtruderTelemetry tm6 = new ExtruderTelemetry()
                .setTime(of(2020, 10, 22, 15, 4, 37,0, UTC));
        return Lists.newArrayList(tm1, tm2, tm3, tm4, tm5, tm6);
    }

    @Test
    @DisplayName("Should return empty list when telemetry is not found")
    void shouldReturnEmptyListWhenRawTelemetryIsNotFound() {
        // given
        OffsetDateTime from = of(2020, 10, 21, 23, 0, 0,0, UTC);
        OffsetDateTime to = of(2020, 10, 22, 23, 0, 0,0, UTC);
        Extruder device = new Extruder();
        device.setId(1);
        device.setCircumference(BigDecimal.valueOf(100.00));

        given(telemetryRepository.findTelemetriesInPeriod(device.getId(), from, to))
                .willReturn(Optional.of(new ArrayList<>()));

        // when
        List<ExtruderTelemetryReport> actual = generator.generateReportRecordsForDevice(device, from, to);

        // then
        assertThat(actual).isEmpty();
        verifyNoInteractions(calculator, telemetryReportRepository);
        verify(telemetryRepository, never()).deleteAll();
    }
}
