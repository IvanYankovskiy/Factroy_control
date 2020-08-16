package com.factory.control.repository.device;

import com.factory.control.domain.entities.device.Extruder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtruderRepository extends JpaRepository<Extruder, Integer> {
}
