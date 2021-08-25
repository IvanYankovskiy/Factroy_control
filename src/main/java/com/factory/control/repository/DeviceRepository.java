package com.factory.control.repository;

import com.factory.control.domain.entities.Device;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends DeviceBaseRepository<Device, Integer> {
}
