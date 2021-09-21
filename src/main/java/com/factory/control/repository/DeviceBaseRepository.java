package com.factory.control.repository;

import com.factory.control.domain.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface DeviceBaseRepository<E extends Device, ID extends Serializable> extends JpaRepository<E, ID> {

    E findByUuid(String uuid);
}
