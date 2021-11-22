package com.factory.control.repository;

import com.factory.control.domain.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface DeviceBaseRepository<E extends Device, ID extends Serializable> extends JpaRepository<E, ID> {

    E findByUuid(String uuid);

    @Query("select distinct(d) from Device d where d.id in :ids or d.name in :names or d.uuid in :uuids")
    List<E> findByCriteria(
            @Param("ids") List<Integer> ids,
            @Param("names") List<String> names,
            @Param("uuids") List<String> uuids
    );
}
