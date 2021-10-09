package com.factory.control.service.aggregation;

import com.factory.control.domain.bo.Temp;

import java.time.Instant;

class Input implements Temp<Instant> {
    private final String id;
    private final Instant time;

    public Input(String id, Instant time) {
        this.id = id;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    @Override
    public Instant getTime() {
        return this.time;
    }
}
