package com.factory.control.controller.mapper;

import org.springframework.stereotype.Component;

import java.time.temporal.Temporal;
import java.util.function.Function;

@Component
public class DateTimeMapper {

    public <I extends Temporal, O extends Temporal> O mapToType(I input, Function<I, O> method) {
        return method.apply(input);
    }
}
