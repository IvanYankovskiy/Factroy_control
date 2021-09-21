package com.factory.control.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DeviceIsNotFoundException extends RuntimeException {

    public DeviceIsNotFoundException(String deviceUuid) {
        super("Device with uuid \"" + deviceUuid + "\" is not found.");
    }
}
