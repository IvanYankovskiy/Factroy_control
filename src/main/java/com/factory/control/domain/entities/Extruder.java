package com.factory.control.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
