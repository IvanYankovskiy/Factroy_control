package com.factory.control.controller.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExtruderTelemetryReportDTO implements Serializable {

    private Double length;

    private Double density;

    private Integer forLastHours;

    private Integer forLasMinutes;

}
