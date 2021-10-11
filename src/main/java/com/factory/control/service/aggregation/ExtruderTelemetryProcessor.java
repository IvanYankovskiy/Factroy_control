package com.factory.control.service.aggregation;

import com.factory.control.domain.bo.Temp;
import com.factory.control.domain.entities.ExtruderTelemetry;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class ExtruderTelemetryProcessor implements TimeBasedProcessor<ExtruderTelemetry, ExtruderTelemetry> {

    @Override
    public List<ExtruderTelemetry> reduce(List<ExtruderTelemetry> input) {
        input.sort(Comparator.comparing(Temp::getTime));
        // TODO: implement grouping by Device
        Integer total = input.stream().reduce(0, (counter, item) -> counter + item.getCounter(), Integer::sum);
        ExtruderTelemetry latestItem = input.get(input.size() - 1);

        return Collections.singletonList(
                new ExtruderTelemetry()
                        .setCounter(total)
                        .setTime(latestItem.getTime())
                        .setDevice(latestItem.getDevice())
        );
    }
}
