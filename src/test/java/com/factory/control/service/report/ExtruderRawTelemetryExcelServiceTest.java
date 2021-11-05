package com.factory.control.service.report;

import com.factory.control.domain.entities.DeviceType;
import com.factory.control.domain.entities.Extruder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ExtruderRawTelemetryExcelServiceTest {

    private ExtruderRawTelemetryExcelService service = new ExtruderRawTelemetryExcelService();

    @Test
    void testCreateAnyDescriptionWhenInputIsExtruder() {
        Extruder entity = new Extruder();
        entity.setUuid("uuid");
        entity.setName("device 1");
        entity.setDeviceType(DeviceType.EXTRUDER);
        entity.setDescription("test description");
        entity.setCircumference(BigDecimal.valueOf(123.85));

        Map<String, Object> actual = service.createAnyDescription(entity);

        assertThat(actual).isNotNull();

        assertThat(actual.get("uuid")).isEqualTo("uuid");
        assertThat(actual.get("name")).isEqualTo("device 1");
        assertThat(actual.get("deviceType")).isEqualTo(DeviceType.EXTRUDER.name());
        assertThat(actual.get("description")).isEqualTo("test description");
        assertThat(actual.get("circumference")).isEqualTo(BigDecimal.valueOf(123.85));
    }

}
