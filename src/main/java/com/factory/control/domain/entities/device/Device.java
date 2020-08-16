package com.factory.control.domain.entities.device;

import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "device")
@Inheritance(
        strategy = InheritanceType.JOINED
)
@TypeDef(name = "pgsql_enum",
         typeClass = PostgreSQLEnumType.class)
public class Device {

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "seq_device", sequenceName = "device_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_device")
    private Integer id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false)
    @Type( type = "pgsql_enum")
    private DeviceType deviceType;

    @Column(name = "description", length = 100)
    private String description;

}
