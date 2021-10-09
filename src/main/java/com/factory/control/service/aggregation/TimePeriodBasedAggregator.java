package com.factory.control.service.aggregation;

import com.factory.control.domain.bo.Temp;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;

public class TimePeriodBasedAggregator <E extends Temporal & Comparable<E>, I extends Temp<E>, O extends Temp<E>> {

    private final TimeBasedProcessor<I, O> processor;

    public TimePeriodBasedAggregator(TimeBasedProcessor<I, O> processor) {
        this.processor = processor;
    }

    public List<O> aggregate(List<I> unprocessed, long windowValue, ChronoUnit windowUnit) {
        if (unprocessed.isEmpty()) {
            return Collections.emptyList();
        }
        unprocessed.sort(Comparator.comparing(Temp::getTime));
        return aggregate(unprocessed, buildAggregationSettings(unprocessed, windowValue, windowUnit));
    }

    private AggregationSettings<E> buildAggregationSettings(List<I> unprocessed, long windowValue, ChronoUnit windowUnit) {
        I firstItem = unprocessed.get(0);
        I lastItem = unprocessed.get(unprocessed.size() - 1);
        return new AggregationSettings<>(
                firstItem.getTime(),
                windowUnit.addTo(lastItem.getTime(), windowValue), //because "to" is exclusive
                windowValue,
                windowUnit
        );
    }

    public List<O> aggregate(List<I> unprocessed, AggregationSettings<E> settings) {
        if (unprocessed.isEmpty()) {
            return Collections.emptyList();
        }
        unprocessed.sort(Comparator.comparing(Temp::getTime));
        List<O> results = new ArrayList<>();
        int processedIndex = 0;
        for (
                E from = settings.getFrom();
                from.compareTo(settings.getTo()) < 0;
                from = settings.getWindowUnit().addTo(from, settings.getWindowValue())
        ) {
            E to = getToForSubPeriod(settings, from);
            ListIterator<I> tmIterator = unprocessed.listIterator(processedIndex);
            List<I> collected = new ArrayList<>();
            if (tmIterator.hasNext()) {
                do {
                    I tm = tmIterator.next();
                    if (from.compareTo(tm.getTime()) <= 0 && to.compareTo(tm.getTime()) > 0) {
                        collected.add(tm);
                        processedIndex = tmIterator.nextIndex() - 1;
                    }
                } while (tmIterator.hasNext() );
            }
            if (!collected.isEmpty()) {
                // call processors here
                results.addAll(processor.reduce(collected));
            }
        }

        return results;
    }

    /**
     * <p>Handles situation when "to" for sub period is out of provided bounds. If it is so, then
     * get "to" from general time bound, else compute using current "from" + aggregation window
     * @param settings general aggreagation settings
     * @param currentFrom start of current period for aggregation window
     * @return end of period for current aggregation window
     */
    private E getToForSubPeriod(AggregationSettings<E> settings, E currentFrom) {
        E currentTo = settings.getWindowUnit().addTo(currentFrom, settings.getWindowValue());
        return currentTo.compareTo(settings.getTo()) < 0
                ? currentTo
                : settings.getTo();
    }

}
