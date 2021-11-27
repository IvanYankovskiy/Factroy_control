package com.factory.control.facade;

import com.factory.control.controller.dto.AggregationSettingsDTO;
import com.factory.control.controller.dto.ExtruderRawTelemetryAggregatedReportDTO;
import com.factory.control.controller.mapper.AggregationSettingsMapper;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExtruderTelemetryFacade {
    private final ExtruderTelemetryDomainService extruderTelemetryDomainService;
    private final ExtruderRawTelemetryReportMapper extruderRawTelemetryReportMapper;
    private final ExtruderRawTelemetryExcelService extruderRawTelemetryExcelService;
    private final AggregationSettingsMapper aggregationSettingsMapper;

    public ExtruderRawTelemetryAggregatedReportDTO<OffsetDateTime> aggregateForSingleDevice(
            String uuid,
            AggregationSettingsDTO settings
    ) {
        AggregationSettings<OffsetDateTime> aggregationSettings = aggregationSettingsMapper
                .mapToEntity(settings, OffsetDateTime::from);
        ExtruderRawTelemetryReport<OffsetDateTime> report = extruderTelemetryDomainService
                .aggregateReportForSingleDevice(uuid, aggregationSettings);
        return extruderRawTelemetryReportMapper.mapToDto(report);
    }

    public List<ExtruderRawTelemetryAggregatedReportDTO<OffsetDateTime>> aggregate(
            List<Integer> ids,
            List<String> names,
            List<String> uuids,
            AggregationSettingsDTO settings
    ) {
        AggregationSettings<OffsetDateTime> aggregationSettings = aggregationSettingsMapper
                .mapToEntity(settings, OffsetDateTime::from);
        List<ExtruderRawTelemetryReport<OffsetDateTime>> reports = extruderTelemetryDomainService
                .aggregate(ids, names, uuids, aggregationSettings);
        return reports.stream()
                .map(extruderRawTelemetryReportMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public InMemoryFileContainer aggregateAsExcel(
            List<Integer> ids,
            List<String> names,
            List<String> uuids,
            AggregationSettingsDTO settings
    ) {
        AggregationSettings<OffsetDateTime> aggregationSettings = aggregationSettingsMapper
                .mapToEntity(settings, OffsetDateTime::from);
        List<ExtruderRawTelemetryReport<OffsetDateTime>> reports = extruderTelemetryDomainService
                .aggregate(ids, names, uuids, aggregationSettings);
        return extruderRawTelemetryExcelService.convertToExcel(reports);
    }

    public InMemoryFileContainer aggregateForSingleDeviceAsExcel(
            String uuid,
            AggregationSettingsDTO settings
    ) throws IOException {
        AggregationSettings<OffsetDateTime> aggregationSettings = aggregationSettingsMapper
                .mapToEntity(settings, OffsetDateTime::from);
        ExtruderRawTelemetryReport<OffsetDateTime> report = extruderTelemetryDomainService
                .aggregateReportForSingleDevice(uuid, aggregationSettings);
        return extruderRawTelemetryExcelService.convertToExcel(report);
    }
}
