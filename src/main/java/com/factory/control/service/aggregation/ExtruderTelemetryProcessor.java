package com.factory.control.service.aggregation;

import com.factory.control.domain.bo.Temp;
import com.factory.control.domain.entities.ExtruderTelemetry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class ExtruderTelemetryProcessor implements TimeBasedProcessor<ExtruderTelemetry, ExtruderTelemetry> {

    @Override
    public List<ExtruderTelemetry> reduce(List<ExtruderTelemetry> input) {
        if (input.isEmpty()) return new ArrayList<>();

        input.sort(Comparator.comparing(Temp::getTime));
        // TODO: implement grouping by Device
        List<ExtruderTelemetry> result = new ArrayList<>();
        ExtruderTelemetry aggregatedTm = null;
        for (ExtruderTelemetry tm : input) {
            if (tm.getCounter() == 0) {
                if (aggregatedTm != null) {
                    result.add(aggregatedTm);
                    aggregatedTm = null;
                }
                ExtruderTelemetry idleTm = new ExtruderTelemetry();
                idleTm.setCounter(0);
                idleTm.setTime(tm.getTime());
                idleTm.setDevice(tm.getDevice());
                result.add(idleTm);
            } else {
                if (aggregatedTm == null) {
                    aggregatedTm = new ExtruderTelemetry();
                    aggregatedTm.setCounter(0);
                }
                aggregatedTm.setCounter(aggregatedTm.getCounter() + tm.getCounter());
                aggregatedTm.setTime(tm.getTime());
                aggregatedTm.setDevice(tm.getDevice());
            }
        }
        if (aggregatedTm != null) result.add(aggregatedTm);

        return result;
    }
}
