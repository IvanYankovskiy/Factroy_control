package com.factory.control.controller.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "deviceType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ExtruderDTO.class, name = "EXTRUDER"),
        @JsonSubTypes.Type(value = DeviceDTO.class, name = "ANY")
})
@Data
public class DeviceDTO implements Serializable {

    private String uuid;

    @NotNull(message = "Device type can't be null")
    private String deviceType;

    @NotNull(message = "Device name can't be null")
    private String name;

    @Length(max = 100, message = "Device description can't be more than {max} characters")
    private String description;
}
