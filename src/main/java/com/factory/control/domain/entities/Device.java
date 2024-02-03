package com.factory.control.domain.entities;

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "device")
@Inheritance(
        strategy = InheritanceType.JOINED
)
public class Device {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "seq_device", sequenceName = "device_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_device")
    private Integer id;

    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    private String uuid;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false)
    @Type( value = PostgreSQLEnumType.class)
    private DeviceType deviceType;

    @Column(name = "description", length = 100)
    private String description;

}
