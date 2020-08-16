package com.factory.control.domain.entities.device;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "extruder")
public class Extruder extends Device {

    @Column(name = "circumference", nullable = false)
    private BigDecimal circumference;

}
