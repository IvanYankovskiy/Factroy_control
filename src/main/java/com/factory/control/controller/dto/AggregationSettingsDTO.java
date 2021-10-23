package com.factory.control.controller.dto;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;


@ApiImplicitParams({
        @ApiImplicitParam(name = "from", dataType = "OffsetDateTime", paramType = "query", value = "2021-10-16T16:20:34.000+07:00", required = true),
        @ApiImplicitParam(name = "to", dataType = "OffsetDateTime", paramType = "query", value = "2021-10-16T16:20:34.000+07:00", required = true),
        @ApiImplicitParam(name = "window value", dataType = "int", paramType = "query", value = "1", required = true),
        @ApiImplicitParam(name = "window unit", dataType = "enum", paramType = "query", required = true)
})
@Data
public class AggregationSettingsDTO {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    OffsetDateTime from;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    OffsetDateTime to;
    long windowValue;

    ChronoUnit windowUnit;
}
