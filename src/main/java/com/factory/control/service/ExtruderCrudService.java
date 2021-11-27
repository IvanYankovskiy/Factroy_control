package com.factory.control.service;

import com.factory.control.domain.entities.DeviceType;
import com.factory.control.domain.entities.Extruder;
import com.factory.control.repository.ExtruderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExtruderCrudService extends DeviceCrudServiceAbstract<Extruder, Integer> {

    @Autowired
    public ExtruderCrudService(ExtruderRepository extruderRepository) {
        super(extruderRepository);
    }

    @Override
    String getType() {
        return DeviceType.EXTRUDER.name();
    }

    @Override
    protected void beforeUpdate(Extruder existedDevice, Extruder deviceWish) {
        super.beforeUpdate(existedDevice, deviceWish);
        existedDevice.setCircumference(deviceWish.getCircumference());
    }

    public List<Extruder> findByCriteria(List<Integer> ids, List<String> names, List<String> uuids) {
        return getRepository().findByCriteria(ids, names, uuids);
    }
}
