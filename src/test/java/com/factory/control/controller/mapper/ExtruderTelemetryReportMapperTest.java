package com.factory.control.controller.mapper;

import com.factory.control.controller.dto.report.extruder.ExtruderTelemetryReportDTO;
import com.factory.control.domain.bo.ExtruderTelemetryReport;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExtruderTelemetryReportMapperTest {

    private ExtruderTelemetryReportMapper mapper = Mappers.getMapper(ExtruderTelemetryReportMapper.class);

    @Test
    void test_fromEntityToDto() {
        OffsetDateTime now = OffsetDateTime.now();
        ExtruderTelemetryReport entity = new ExtruderTelemetryReport();
        entity.setLengthPerformance(BigDecimal.valueOf(15.24));
        entity.setVolumetricPerformance(BigDecimal.valueOf(1.54));
        entity.setStartOfPeriod(now.minusHours(1));
        entity.setEndOfPeriod(now);

        ExtruderTelemetryReportDTO dto = mapper.fromEntityToDTO(entity);

        assertNotNull(dto);
    }

}
