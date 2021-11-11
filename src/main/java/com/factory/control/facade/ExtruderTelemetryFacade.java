package com.factory.control.facade;

import com.factory.control.controller.dto.AggregationSettingsDTO;
import com.factory.control.controller.dto.ExtruderRawTelemetryAggregatedReportDTO;
import com.factory.control.controller.mapper.ExtruderRawTelemetryReportMapper;
import com.factory.control.domain.bo.ExtruderRawTelemetryReport;
import com.factory.control.domain.bo.InMemoryFileContainer;
import com.factory.control.service.ExtruderTelemetryDomainService;
import com.factory.control.service.aggregation.AggregationSettings;
import com.factory.control.service.report.ExtruderRawTelemetryExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ExtruderTelemetryFacade {
    private final ExtruderTelemetryDomainService extruderTelemetryDomainService;
    private final ExtruderRawTelemetryReportMapper extruderRawTelemetryReportMapper;
    private final ExtruderRawTelemetryExcelService extruderRawTelemetryExcelService;

    public ExtruderRawTelemetryAggregatedReportDTO<OffsetDateTime> aggregateForSingleDevice(
            String uuid,
            AggregationSettingsDTO settings
    ) {
        AggregationSettings<OffsetDateTime> aggregationSettings = new AggregationSettings<>(
                OffsetDateTime.from(settings.getFrom()),
                OffsetDateTime.from(settings.getTo()),
                settings.getWindowValue(),
                settings.getWindowUnit()
        );
        ExtruderRawTelemetryReport<OffsetDateTime> report = extruderTelemetryDomainService
                .aggregateReportForSingleDevice(uuid, aggregationSettings);
        return extruderRawTelemetryReportMapper.mapToDto(report);
    }

    public InMemoryFileContainer aggregateForSingleDeviceAsExcel(
            String uuid,
            AggregationSettingsDTO settings
    ) throws IOException {
        AggregationSettings<OffsetDateTime> aggregationSettings = new AggregationSettings<>(
                OffsetDateTime.from(settings.getFrom()),
                OffsetDateTime.from(settings.getTo()),
                settings.getWindowValue(),
                settings.getWindowUnit()
        );
        ExtruderRawTelemetryReport<OffsetDateTime> report = extruderTelemetryDomainService
                .aggregateReportForSingleDevice(uuid, aggregationSettings);
        return extruderRawTelemetryExcelService.convertToExcel(report);
    }
}
