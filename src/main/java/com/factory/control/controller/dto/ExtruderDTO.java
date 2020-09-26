package com.factory.control.controller.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@JsonTypeName("EXTRUDER")
@Data
@EqualsAndHashCode(callSuper = true)
public class ExtruderDTO extends DeviceDTO {

    @NotNull
    @Positive
    private BigDecimal circumference;

}
