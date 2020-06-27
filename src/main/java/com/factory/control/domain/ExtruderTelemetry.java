package com.factory.control.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "extruder")
public class ExtruderTelemetry {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "seq_extruder", sequenceName = "extruder_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_extruder")
    private Integer id;

    @Column(name = "device_id", nullable = false)
    private Device deviceId;

    @Column(name = "counter", nullable = false)
    private Integer counter;

    @Column(name = "density", nullable = false, precision = 0)
    private BigInteger density;

    @Column(name = "diameter", nullable = false, precision = 0)
    private BigInteger diameter;

    @Column(name = "time", nullable = false)
    private OffsetDateTime time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtruderTelemetry that = (ExtruderTelemetry) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(deviceId, that.deviceId) &&
                Objects.equals(counter, that.counter) &&
                Objects.equals(density, that.density) &&
                Objects.equals(diameter, that.diameter) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceId, counter, density, diameter, time);
    }
}
