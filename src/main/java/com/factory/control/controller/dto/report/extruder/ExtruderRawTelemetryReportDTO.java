package com.factory.control.controller.dto.report.extruder;

import com.factory.control.controller.dto.ExtruderDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ExtruderRawTelemetryReportDTO implements Serializable {

    ExtruderDTO deviceDTO;

    private List<ExtruderRawTelemetryRecordDTO> records;


}
