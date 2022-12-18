package com.factory.control.facade;

import com.factory.control.controller.dto.AggregationSettingsDTO;
import com.factory.control.controller.dto.ExtruderRawTelemetryAggregatedReportDTO;
import com.factory.control.controller.mapper.AggregationSettingsMapper;
import com.factory.control.controller.mapper.ExtruderTelemetryReportMapper;
import com.factory.control.domain.bo.ExtruderTelemetryReport;
import com.factory.control.domain.bo.InMemoryFileContainer;
import com.factory.control.service.ExtruderTelemetryDomainService;
import com.factory.control.service.aggregation.AggregationSettings;
import com.factory.control.service.report.ExtruderRawTelemetryExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExtruderTelemetryFacade {
    private final ExtruderTelemetryDomainService extruderTelemetryDomainService;
    private final ExtruderTelemetryReportMapper extruderTelemetryReportMapper;
    private final ExtruderRawTelemetryExcelService extruderRawTelemetryExcelService;
    private final AggregationSettingsMapper aggregationSettingsMapper;

    public ExtruderRawTelemetryAggregatedReportDTO<OffsetDateTime> aggregateForSingleDevice(
            String uuid,
            AggregationSettingsDTO settings
    ) {
        AggregationSettings<OffsetDateTime> aggregationSettings = aggregationSettingsMapper
                .mapToEntity(settings, OffsetDateTime::from);
        ExtruderTelemetryReport<OffsetDateTime> report = extruderTelemetryDomainService
                .aggregateReportForSingleDevice(uuid, aggregationSettings);
        return extruderTelemetryReportMapper.mapToDto(report);
    }

    public List<ExtruderRawTelemetryAggregatedReportDTO<OffsetDateTime>> aggregate(
            List<Integer> ids,
            List<String> names,
            List<String> uuids,
            AggregationSettingsDTO settings
    ) {
        AggregationSettings<OffsetDateTime> aggregationSettings = aggregationSettingsMapper
                .mapToEntity(settings, OffsetDateTime::from);
        List<ExtruderTelemetryReport<OffsetDateTime>> reports = extruderTelemetryDomainService
                .aggregate2(ids, names, uuids, aggregationSettings);
        return reports.stream()
                .map(extruderTelemetryReportMapper::mapToDto)
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
        List<ExtruderTelemetryReport<OffsetDateTime>> reports = extruderTelemetryDomainService
                .aggregate2(ids, names, uuids, aggregationSettings);
        return extruderRawTelemetryExcelService.convertToExcel(reports);
    }
}
