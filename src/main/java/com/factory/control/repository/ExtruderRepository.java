package com.factory.control.repository;

import com.factory.control.domain.entities.ExtruderTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtruderRepository extends JpaRepository<ExtruderTelemetry, Integer> {

}
