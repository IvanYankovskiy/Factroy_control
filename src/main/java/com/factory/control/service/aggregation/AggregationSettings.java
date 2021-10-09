package com.factory.control.service.aggregation;

import lombok.Value;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

@Value
public class AggregationSettings<T extends Temporal> {
    T from;
    T to;
    long windowValue;
    ChronoUnit windowUnit;
}
