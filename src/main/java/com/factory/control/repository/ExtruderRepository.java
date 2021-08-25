package com.factory.control.repository;

import com.factory.control.domain.entities.Extruder;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtruderRepository extends DeviceBaseRepository<Extruder, Integer> {
}
