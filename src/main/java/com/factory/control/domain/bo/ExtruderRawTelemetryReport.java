package com.factory.control.domain.bo;

import com.factory.control.domain.entities.Extruder;
import com.factory.control.domain.entities.ExtruderTelemetry;
import com.factory.control.service.aggregation.AggregationSettings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.temporal.Temporal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtruderRawTelemetryReport<T extends Temporal> {
    Extruder extruder;
    AggregationSettings<T> aggregationSettings;
    List<ExtruderTelemetry> telemetry;
}
