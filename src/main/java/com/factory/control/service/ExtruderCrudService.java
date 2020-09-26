package com.factory.control.service;

import com.factory.control.domain.entities.device.DeviceType;
import com.factory.control.domain.entities.device.Extruder;
import com.factory.control.repository.device.DeviceBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExtruderCrudService extends DeviceCrudServiceAbstract<Extruder, Integer> {

    @Autowired
    public ExtruderCrudService(DeviceBaseRepository<Extruder, Integer> extruderRepository) {
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

}
