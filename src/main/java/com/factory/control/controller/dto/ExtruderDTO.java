package com.factory.control.controller.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExtruderDTO extends DeviceDTO {

    @NotNull
    @Positive
    private BigDecimal circumference;

}
