package com.factory.control.service.aggregation;

import com.factory.control.domain.entities.ExtruderTelemetry;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExtruderTelemetryAggregatorTest {

    private ExtruderTelemetryAggregator aggregator = new ExtruderTelemetryAggregator();

    @Test
    void testReduceExtruderTelemetry() {
        // given
        List<ExtruderTelemetry> extruderTelemetryList = createExtruderTelemetryList();

        // when
        List<ExtruderTelemetry> actual = aggregator.reduce(extruderTelemetryList);

        // then
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0))
                .usingRecursiveComparison()
                .ignoringFields("id", "device", "diameter", "density")
                .isEqualTo(
                        new ExtruderTelemetry()
                                .setCounter(40)
                                .setTime(OffsetDateTime.parse("2020-10-21T23:02:07.245+00:00"))
                );
    }

    private List<ExtruderTelemetry> createExtruderTelemetryList() {
        return Arrays.asList(
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:00:17.245+00:00"))
                        .setCounter(10),
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:02:07.245+00:00"))
                        .setCounter(20),
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:01:22.245+00:00"))
                        .setCounter(10)
        );
    }

}
