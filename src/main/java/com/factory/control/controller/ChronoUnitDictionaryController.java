package com.factory.control.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChronoUnitDictionaryController {

    @GetMapping("/dictionary/chrono-unit")
    public List<String> getChronoUnitValues() {
        return Arrays.stream(ChronoUnit.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
