package com.factory.control.controller.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DeviceDTO implements Serializable {

    private String token;

    @NotNull(message = "Device type can't be null")
    private String deviceType;

    @NotNull(message = "Device name can't be null")
    private String name;

    @Length(max = 100, message = "Device description can't be more than {max} characters")
    private String description;
}
