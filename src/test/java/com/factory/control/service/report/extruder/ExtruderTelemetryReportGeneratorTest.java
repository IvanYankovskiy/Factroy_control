package com.factory.control.service.report.extruder;

import com.factory.control.repository.ExtruderTelemetryReportRepository;
import com.factory.control.repository.ExtruderTelemetryRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExtruderTelemetryReportGeneratorTest {

    @InjectMocks
    ExtruderTelemetryReportGenerator generator;

    @Mock
    private ExtruderTelemetryRepository telemetryRepository;



}
