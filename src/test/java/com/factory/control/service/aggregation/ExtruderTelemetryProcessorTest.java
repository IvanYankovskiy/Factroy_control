package com.factory.control.service.aggregation;

import com.factory.control.domain.entities.ExtruderTelemetry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ExtruderTelemetryProcessorTest {

    private ExtruderTelemetryProcessor aggregator = new ExtruderTelemetryProcessor();

    @Test
    @DisplayName("Reduce unsorted telemetry without zeroes")
    void testReduceExtruderTelemetry() {
        // given
        List<ExtruderTelemetry> extruderTelemetryList = createExtruderTelemetryListForUnsortedWithoutZeroes();

        // when
        List<ExtruderTelemetry> actual = aggregator.reduce(extruderTelemetryList);

        // then
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0))
                .usingRecursiveComparison()
                .ignoringFields("id", "device")
                .isEqualTo(
                        new ExtruderTelemetry()
                                .setCounter(40)
                                .setTime(OffsetDateTime.parse("2020-10-21T23:02:07.245+00:00"))
                );
    }

    private List<ExtruderTelemetry> createExtruderTelemetryListForUnsortedWithoutZeroes() {
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

    @Test
    @DisplayName("Reduce unsorted telemetry with zeroes")
    void testReduceUnsortedWithZeroes() {
        // given
        List<ExtruderTelemetry> extruderTelemetryList = createExtruderTelemetryListForUnsortedWithZeroes();

        // when
        List<ExtruderTelemetry> actual = aggregator.reduce(extruderTelemetryList);

        // then
        assertThat(actual).hasSize(4);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "device")
                .isEqualTo(
                        List.of(
                                new ExtruderTelemetry()
                                        .setCounter(0)
                                        .setTime(OffsetDateTime.parse("2020-10-21T22:59:07.130+00:00")),
                                new ExtruderTelemetry()
                                        .setCounter(20)
                                        .setTime(OffsetDateTime.parse("2020-10-21T23:01:22.245+00:00")),
                                new ExtruderTelemetry()
                                        .setCounter(0)
                                        .setTime(OffsetDateTime.parse("2020-10-21T23:01:55.155+00:00")),
                                new ExtruderTelemetry()
                                        .setCounter(30)
                                        .setTime(OffsetDateTime.parse("2020-10-21T23:02:07.245+00:00"))
                        )

                );
    }

    private List<ExtruderTelemetry> createExtruderTelemetryListForUnsortedWithZeroes() {
        return Arrays.asList(
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:01:55.155+00:00")) // 3 <-- 0
                        .setCounter(0),
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:00:17.245+00:00")) // 2.1
                        .setCounter(10),
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:02:07.245+00:00")) // 4
                        .setCounter(30),
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:01:22.245+00:00")) // 2.2
                        .setCounter(10),
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T22:59:07.130+00:00")) // 1 <-- 0
                        .setCounter(0)
        );
    }

    @Test
    void testReduceEmptyList() {
        // when
        List<ExtruderTelemetry> actual = aggregator.reduce(List.of());

        // then
        assertThat(actual).isEmpty();
    }

    @Test
    void testReduceListWithZeroInTheEnd() {
        // when
        List<ExtruderTelemetry> actual = createExtruderTelemetryListWithZeroInTheEnd();

        // then
        assertThat(actual).hasSize(2);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "device")
                .isEqualTo(
                        List.of(
                                new ExtruderTelemetry()
                                        .setCounter(10)
                                        .setTime(OffsetDateTime.parse("2020-10-21T23:00:17.245+00:00")),
                                new ExtruderTelemetry()
                                        .setCounter(0)
                                        .setTime(OffsetDateTime.parse("2020-10-21T23:02:07.245+00:00"))
                        )
                );
    }

    private List<ExtruderTelemetry> createExtruderTelemetryListWithZeroInTheEnd() {
        return Arrays.asList(
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:00:17.245+00:00"))
                        .setCounter(10),
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:02:07.245+00:00"))
                        .setCounter(0)
        );
    }

    @Test
    void testReduceListWithAllZeroes() {
        // when
        List<ExtruderTelemetry> actual = createExtruderTelemetryListWithAllZeroes();

        // then
        assertThat(actual).hasSize(2);
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "device")
                .isEqualTo(
                        List.of(
                                new ExtruderTelemetry()
                                        .setCounter(0)
                                        .setTime(OffsetDateTime.parse("2020-10-21T23:00:17.245+00:00")),
                                new ExtruderTelemetry()
                                        .setCounter(0)
                                        .setTime(OffsetDateTime.parse("2020-10-21T23:02:07.245+00:00"))
                        )
                );
    }

    private List<ExtruderTelemetry> createExtruderTelemetryListWithAllZeroes() {
        return Arrays.asList(
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:00:17.245+00:00"))
                        .setCounter(0),
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:02:07.245+00:00"))
                        .setCounter(0)
        );
    }
}
