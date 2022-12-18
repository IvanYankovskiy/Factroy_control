package com.factory.control.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.OffsetDateTime;

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

    @Column(name = "time", nullable = false)
    private OffsetDateTime time;

}
