package com.factory.control.controller.dto;

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
