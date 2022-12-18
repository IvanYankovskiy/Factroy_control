package com.factory.control.service;

import com.factory.control.IntegrationTest;
import com.factory.control.domain.entities.*;
import com.factory.control.repository.ExtruderRepository;
import com.factory.control.repository.ExtruderTelemetryRepository;
import com.factory.control.service.aggregation.AggregationSettings;
import com.factory.control.service.aggregation.TelemetryAggregator;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.assertj.core.internal.OffsetDateTimeByInstantComparator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class ExtruderTelemetryDomainServiceIT {

    @Autowired
    private TelemetryAggregator telemetryAggregator;
    @Autowired
    private ExtruderRepository extruderRepository;
    @Autowired
    private ExtruderTelemetryRepository extruderTelemetryRepository;

    @Transactional
    @Test
    void shouldAggregateTelemetry() {
        // given
        Extruder extruder = saveExtruderCase1();
        Integer extruderId = extruder.getId();
        saveTelemetry(extruder);
        TestTransaction.flagForCommit();
        TestTransaction.end();

        AggregationSettings<OffsetDateTime> settings = new AggregationSettings<>(
                OffsetDateTime.parse("2020-10-21T22:55:00.000+00:00"),
                OffsetDateTime.parse("2020-10-22T01:25:00.000+00:00"),
                5,
                ChronoUnit.MINUTES
        );


        // when
        List<ExtruderTelemetryDto> actual = telemetryAggregator.aggregateUsingTimeIteration(settings, extruder);


        // then
        assertThat(actual).hasSize(8);
        RecursiveComparisonConfiguration recursiveComparisonConfiguration = RecursiveComparisonConfiguration.builder()
                .withComparatorForType(OffsetDateTimeByInstantComparator.getInstance(), OffsetDateTime.class)
                .withIgnoreCollectionOrder(true)
                .build();
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparator(recursiveComparisonConfiguration)
                .containsExactlyInAnyOrderElementsOf(
                        List.of(
                                new ExtruderTelemetryDto(extruderId, 0, OffsetDateTime.parse("2020-10-21T22:59:07.130+00:00")),
                                new ExtruderTelemetryDto(extruderId, 20, OffsetDateTime.parse("2020-10-21T23:01:22.245+00:00")),
                                new ExtruderTelemetryDto(extruderId, 60, OffsetDateTime.parse("2020-10-21T23:08:07.245+00:00")),
                                new ExtruderTelemetryDto(extruderId, 40, OffsetDateTime.parse("2020-10-22T00:04:39.245+00:00")),
                                new ExtruderTelemetryDto(extruderId, 40, OffsetDateTime.parse("2020-10-22T00:09:02.245+00:00")),
                                new ExtruderTelemetryDto(extruderId, 5, OffsetDateTime.parse("2020-10-22T01:04:39.245+00:00")),
                                new ExtruderTelemetryDto(extruderId, 5, OffsetDateTime.parse("2020-10-22T01:09:02.245+00:00")),
                                new ExtruderTelemetryDto(extruderId, 4, OffsetDateTime.parse("2020-10-22T01:24:59.245+00:00"))
                        )

                );

    }

    private Extruder extruder() {
        Extruder extruder = new Extruder();
        extruder.setCircumference(BigDecimal.valueOf(123.0));
        extruder.setName("extruder-1");
        extruder.setUuid(UUID.randomUUID().toString());
        extruder.setDescription("shouldAggregateTelemetry test extruder");
        extruder.setDeviceType(DeviceType.EXTRUDER);
        return extruder;
    }

    private Extruder saveExtruderCase1() {
        return extruderRepository.save(extruder());
    }

    private List<ExtruderTelemetry> saveTelemetry(Extruder extruder) {
        List<ExtruderTelemetry> telemetry = extruderTelemetry().stream()
                .map(et -> et.setDevice(extruder))
                .collect(Collectors.toList());
        return extruderTelemetryRepository.saveAll(telemetry);
    }


    private List<ExtruderTelemetry> extruderTelemetry() {
        return List.of(
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T22:59:07.130+00:00")) // 1 <-- 0
                        .setCounter(0),
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:00:17.245+00:00")) // 2.1
                        .setCounter(10),
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:01:22.245+00:00")) // 2.2
                        .setCounter(10),

                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:05:07.245+00:00")) // 3.1
                        .setCounter(30),
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-21T23:08:07.245+00:00")) // 3.2
                        .setCounter(30),

                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-22T00:04:39.245+00:00")) // 4.1
                        .setCounter(40),
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-22T00:09:02.245+00:00")) // 5.1
                        .setCounter(40),

                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-22T01:04:39.245+00:00")) // 6.1
                        .setCounter(5),
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-22T01:09:02.245+00:00")) // 7.1
                        .setCounter(5),

                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-22T01:22:02.245+00:00")) // 8.1
                        .setCounter(2),
                new ExtruderTelemetry()
                        .setTime(OffsetDateTime.parse("2020-10-22T01:24:59.245+00:00")) // 8.2
                        .setCounter(2)
        );
    }


}
