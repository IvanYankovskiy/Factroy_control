package com.factory.control.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class ExtruderTelemetryDTO implements Serializable {

    @NotNull
    private Integer counter;

    @NotNull
    private Double density;

    @NotNull
    private Double diameter;

}
