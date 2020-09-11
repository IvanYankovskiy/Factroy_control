package com.factory.control.repository.device;

import com.factory.control.domain.entities.device.Extruder;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtruderRepository extends DeviceBaseRepository<Extruder, Integer> {
}
