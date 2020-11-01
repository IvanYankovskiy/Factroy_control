package com.factory.control.domain.entities;

import com.factory.control.domain.entities.device.Device;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "extruder_telemetry_report")
@Getter
@Setter
public class ExtruderTelemetryReport {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "seq_extruder_tm_report", sequenceName = "extruder_tm_report_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_extruder_tm_report")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(name = "length", nullable = false)
    BigDecimal length;

    @Column(name = "weight", nullable = false)
    BigDecimal weight;

    @Column(name = "time", nullable = false)
    private OffsetDateTime time;
}
