package com.factory.control.controller.dto.report.extruder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ExtruderTelemetryReportTotalDTO {
    String extruderName;
    BigDecimal totalLength = BigDecimal.valueOf(0.0);
    BigDecimal totalWeight = BigDecimal.valueOf(0.0);

    @JsonProperty("from")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    OffsetDateTime from;

    @JsonProperty("to")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    OffsetDateTime to;

    List<ExtruderTelemetryReportDTO> details;

    public void addLength(BigDecimal length) {
        this.totalLength = totalLength.add(length);
    }

    public void addWeight(BigDecimal weight) {
        this.totalWeight = totalWeight.add(weight);
    }
}
