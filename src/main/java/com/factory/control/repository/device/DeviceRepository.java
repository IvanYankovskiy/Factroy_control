package com.factory.control.repository.device;

import com.factory.control.domain.entities.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    Device findByToken(String token);
}
