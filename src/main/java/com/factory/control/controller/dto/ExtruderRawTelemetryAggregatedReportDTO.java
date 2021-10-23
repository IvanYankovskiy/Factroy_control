package com.factory.control.controller.dto;

import com.factory.control.service.aggregation.AggregationSettings;
import lombok.Value;

import java.time.temporal.Temporal;
import java.util.List;

@Value
public class ExtruderRawTelemetryAggregatedReportDTO<T extends Temporal> {
    ExtruderDTO deviceDto;
    AggregationSettings<T> aggregationSettings;
    List<ExtruderRawTelemetryRecordDTO> records;
}
