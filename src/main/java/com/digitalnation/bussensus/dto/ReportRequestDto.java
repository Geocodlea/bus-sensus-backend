package com.digitalnation.bussensus.dto;

import java.time.LocalDateTime;

public record ReportRequestDto(

        Integer reportId,

        Integer busId,

        Integer routeId,

        Integer stationId,

        int noOfPassengers,

        LocalDateTime dateTime
) {
}