package com.factory.control.service.aggregation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

@Value
public class AggregationSettings<T extends Temporal> {
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    T from;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    T to;
    long windowValue;
    ChronoUnit windowUnit;
}
