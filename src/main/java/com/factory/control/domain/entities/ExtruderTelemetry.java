package com.factory.control.domain.entities;

import com.factory.control.domain.entities.device.Device;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "extruder_telemetry")
public class ExtruderTelemetry {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "seq_extruder", sequenceName = "extruder_tm_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_extruder")
    private Integer id;

    @Access(AccessType.PROPERTY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(name = "counter", nullable = false)
    private Integer counter;

    @Column(name = "density", nullable = false, precision = 0)
    private BigDecimal density;

    @Column(name = "diameter", nullable = false, precision = 0)
    private BigDecimal diameter;

    @Column(name = "time", nullable = false)
    private OffsetDateTime time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtruderTelemetry that = (ExtruderTelemetry) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(device, that.device) &&
                Objects.equals(counter, that.counter) &&
                Objects.equals(density, that.density) &&
                Objects.equals(diameter, that.diameter) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, device, counter, density, diameter, time);
    }
}
