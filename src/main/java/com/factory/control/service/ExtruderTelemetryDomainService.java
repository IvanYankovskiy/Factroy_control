package com.factory.control.service;

import com.factory.control.domain.bo.ExtruderTelemetryReport;
import com.factory.control.domain.entities.Extruder;
import com.factory.control.domain.entities.ExtruderTelemetryDto;
import com.factory.control.service.aggregation.AggregationSettings;
import com.factory.control.service.aggregation.TelemetryAggregator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExtruderTelemetryDomainService {
    private final DeviceCrudServiceAbstract<Extruder, Integer> extruderDeviceService;
    private final TelemetryAggregator telemetryAggregator;


    public List<ExtruderTelemetryReport<OffsetDateTime>> aggregate2(
            List<Integer> ids,
            List<String> names,
            List<String> uuids,
            AggregationSettings<OffsetDateTime> timeSettings
    ) {
        return extruderDeviceService.findByCriteria(ids, names, uuids).stream()
                .map(extruder -> {
                    List<ExtruderTelemetryDto> aggregatedTelemetry = this.aggregateForSingleDevice(timeSettings, extruder);
                    return new ExtruderTelemetryReport<>(extruder, timeSettings, aggregatedTelemetry);
                })
                .collect(Collectors.toList());
    }

    public ExtruderTelemetryReport<OffsetDateTime> aggregateReportForSingleDevice(
            String uuid,
            AggregationSettings<OffsetDateTime> timeSettings
    ) {
        Extruder extruder = extruderDeviceService.selectByUuid(uuid);
        List<ExtruderTelemetryDto> telemetry = aggregateForSingleDevice(timeSettings, extruder);
        return new ExtruderTelemetryReport<>(extruder, timeSettings, telemetry);
    }

    private List<ExtruderTelemetryDto> aggregateForSingleDevice(AggregationSettings<OffsetDateTime> timeSettings, Extruder extruder) {
        log.debug("Starting aggregation for \"" + extruder.getName() + "\".");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("Aggregation new");
        List<ExtruderTelemetryDto> extruderTelemetryDtos = telemetryAggregator.aggregateUsingTimeIteration(timeSettings, extruder);
        stopWatch.stop();
        log.debug("Aggregation for \"" + extruder.getName() + "\" completed. Elapsed time: " + stopWatch.getTotalTimeSeconds() + " seconds.");
        return extruderTelemetryDtos;
    }
}
