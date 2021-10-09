package com.factory.control.service.aggregation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.time.Instant.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimePeriodBasedAggregatorTest {

    @InjectMocks
    private TimePeriodBasedAggregator<Instant, Input, Output> aggregator;

    @Mock
    private TimeBasedProcessor<Input, Output> processor;

    @Captor
    private ArgumentCaptor<List<Input>> inputArgumentCaptor;

    @Test
    void testWhenInputEmptyThenOutputEmpty() {
        // given
        List<Input> inputs = Collections.emptyList();
        AggregationSettings<Instant> settings = new AggregationSettings<>(
                parse("2020-10-21T23:00:00Z"),
                parse("2020-10-21T23:10:00Z"),
                5,
                ChronoUnit.MINUTES
        );

        // when
        aggregator.aggregate(inputs, settings);

        // then
        verifyNoInteractions(processor);
    }

    @Test
    void testAggregationForExplicitlyBoundedTimeRange() {
        // given
        List<Input> inputs = createInputItemsForExplicitlyBoundedTimeRange();
        AggregationSettings<Instant> settings = new AggregationSettings<>(
                parse("2020-10-21T23:00:00Z"),
                parse("2020-10-21T23:11:00Z"),
                2,
                ChronoUnit.MINUTES
        );

        // when
        aggregator.aggregate(inputs, settings);

        // then
        verify(processor, times(4)).reduce(inputArgumentCaptor.capture());
        List<List<Input>> allValues = inputArgumentCaptor.getAllValues();

        assertThat(allValues).hasSize(4);
        // verify the first group
        assertThat(allValues.get(0)).extracting(Input::getId).contains("group-1-item-1", "group-1-item-2");
        // verify the second group
        assertThat(allValues.get(1)).extracting(Input::getId).contains("group-2-item-1", "group-2-item-2");
        // verify the third group
        assertThat(allValues.get(2)).extracting(Input::getId).contains("group-3-item-1", "group-3-item-2");
        // verify the fourth group
        assertThat(allValues.get(3)).extracting(Input::getId).contains("group-4-item-1");
    }

    private List<Input> createInputItemsForExplicitlyBoundedTimeRange() {
        return Arrays.asList(
                new Input("Excluded by provided time bounds 1", parse("2020-10-21T22:59:35Z")),
                new Input("group-1-item-1", parse("2020-10-21T23:00:17.245Z")),
                new Input("group-1-item-2", parse("2020-10-21T23:00:32Z")),
                new Input("group-2-item-1", parse("2020-10-21T23:02:44.245Z")),
                new Input("group-2-item-2", parse("2020-10-21T23:03:32Z")),
                new Input("group-3-item-1", parse("2020-10-21T23:06:44.245Z")),
                new Input("group-3-item-2", parse("2020-10-21T23:07:32Z")),
                new Input("group-4-item-1", parse("2020-10-21T23:10:35Z")),
                new Input("Excluded by provided time bounds 2", parse("2020-10-21T23:11:35Z"))
        );
    }

    @Test
    void testAggregationForNonExplicitlyBoundedTimeRange() {
        // given
        List<Input> inputs = createInputItemsForNonExplicitlyBoundedTimeRange();

        // when
        aggregator.aggregate(inputs, 2, ChronoUnit.MINUTES);

        // then
        verify(processor, times(5)).reduce(inputArgumentCaptor.capture());
        List<List<Input>> allValues = inputArgumentCaptor.getAllValues();

        assertThat(allValues).hasSize(5);
        // verify the first group
        assertThat(allValues.get(0)).extracting(Input::getId).contains("group-1-item-1", "group-1-item-2", "group-1-item-3");
        // verify the second group
        assertThat(allValues.get(1)).extracting(Input::getId).contains("group-2-item-1", "group-2-item-2");
        // verify the third group
        assertThat(allValues.get(2)).extracting(Input::getId).contains("group-3-item-1", "group-3-item-2");
        // verify the fourth group
        assertThat(allValues.get(3)).extracting(Input::getId).contains("group-4-item-1");
        // verify the fifth group
        assertThat(allValues.get(4)).extracting(Input::getId).contains("group-5-item-1");
    }

    private List<Input> createInputItemsForNonExplicitlyBoundedTimeRange() {
        return Arrays.asList(
                // from 2020-10-21T22:59:35Z to 2020-10-21T23:01:35Z
                new Input("group-1-item-1", parse("2020-10-21T22:59:35Z")),
                new Input("group-1-item-2", parse("2020-10-21T23:00:17.245Z")),
                new Input("group-1-item-3", parse("2020-10-21T23:00:32Z")),
                // from 2020-10-21T23:01:35Z to 2020-10-21T23:03:35Z
                new Input("group-2-item-1", parse("2020-10-21T23:02:44.245Z")),
                new Input("group-2-item-2", parse("2020-10-21T23:03:32Z")),
                // from 2020-10-21T23:05:35Z to 2020-10-21T23:07:35Z
                new Input("group-3-item-1", parse("2020-10-21T23:06:44.245Z")),
                new Input("group-3-item-2", parse("2020-10-21T23:07:32Z")),
                // from 2020-10-21T23:09:35Z to 2020-10-21T23:11:35Z
                new Input("group-4-item-1", parse("2020-10-21T23:10:35Z")),
                // from 2020-10-21T23:11:35Z to 2020-10-21T23:13:35Z
                new Input("group-5-item-1", parse("2020-10-21T23:11:35Z"))
        );
    }
}
