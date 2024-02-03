package com.factory.control.controller.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class ExtruderTelemetryDTO implements Serializable {
    @NotNull
    private Integer counter;
}
