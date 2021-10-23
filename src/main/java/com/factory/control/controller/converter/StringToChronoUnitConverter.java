package com.factory.control.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component
public class StringToChronoUnitConverter implements Converter<String, ChronoUnit> {
    @Override
    public ChronoUnit convert(String source) {
        return ChronoUnit.valueOf(source.toUpperCase());
    }
}
