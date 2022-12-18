package com.factory.control.service.aggregation;

import com.factory.control.config.properties.AppProperties;
import com.factory.control.domain.entities.Extruder;
import com.factory.control.domain.entities.ExtruderTelemetryDto;
import com.factory.control.repository.ExtruderTelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TelemetryAggregator {
    private final ExtruderTelemetryRepository telemetryRepository;
    private final AppProperties appProperties;

    public List<ExtruderTelemetryDto> aggregateUsingTimeIteration(AggregationSettings<OffsetDateTime> settings, Extruder extruder) {
        var tmSlice = telemetryRepository.findTelemetryProjectionsInPeriod(
                extruder.getId(),
                settings.getFrom(),
                settings.getTo(),
                PageRequest.of(0, appProperties.getPageSize())
        );
        var results = new ArrayList<ExtruderTelemetryDto>();
        var sContent = tmSlice.getContent();
        ExtruderTelemetryDto subResult = null;
        var sIterator = sContent.listIterator();
        if (!sIterator.hasNext()) {
            return Collections.emptyList();
        }

        for (
                var from = settings.getFrom();
                from.isBefore(settings.getTo());
                from = settings.getWindowUnit().addTo(from, settings.getWindowValue())
        ) {
            var timePeriodIsActual = true;
            var to = getToForSubPeriod(settings, from);
            while (timePeriodIsActual) {
                if (!sIterator.hasNext()) {
                    if (tmSlice.hasNext()) {
                        tmSlice = telemetryRepository.findTelemetryProjectionsInPeriod(
                                extruder.getId(),
                                settings.getFrom(),
                                settings.getTo(),
                                tmSlice.nextPageable()
                        );
                        sContent = tmSlice.getContent();
                        sIterator = sContent.listIterator();
                    } else {
                        if (subResult != null) {
                            results.add(subResult);
                            subResult = null;
                        }
                        break;
                    }
                }
                var currentTm = sIterator.next();
                timePeriodIsActual = from.isBefore(currentTm.getTime()) && to.isAfter(currentTm.getTime());
                if (timePeriodIsActual) {
                    if (subResult == null) {
                        subResult = new ExtruderTelemetryDto(extruder.getId(), 0, to);
                    }
                    subResult.addCount(currentTm.getCounter());
                    subResult.setTime(currentTm.getTime());
                    subResult.setDeviceId(currentTm.getDeviceId());
                } else {
                    if (subResult != null) {
                        results.add(subResult);
                        subResult = null;
                    }
                    sIterator.previous();
                }
            }
        }
        return results;
    }

    public List<ExtruderTelemetryDto> aggregateUsingContentIteration(AggregationSettings<OffsetDateTime> timeSettings, Extruder extruder) {
        // TODO: implement
        return Collections.emptyList();
    }

    /**
     * <p>Handles situation when "to" for sub period is out of provided bounds. If it is so, then
     * get "to" from general time bound, else compute using current "from" + aggregation window
     * @param settings general aggreagation settings
     * @param currentFrom start of current period for aggregation window
     * @return end of period for current aggregation window
     */
    private <E extends Temporal & Comparable<E>> E getToForSubPeriod(AggregationSettings<E> settings, E currentFrom) {
        E currentTo = settings.getWindowUnit().addTo(currentFrom, settings.getWindowValue());
        return currentTo.compareTo(settings.getTo()) < 0
                ? currentTo
                : settings.getTo();
    }
}
