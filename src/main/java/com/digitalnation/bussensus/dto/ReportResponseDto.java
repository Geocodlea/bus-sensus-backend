package com.digitalnation.bussensus.dto;

import java.time.LocalDateTime;

public record ReportResponseDto(

        Integer reportId,

        Integer busId,

        String busName,

        Integer routeId,

        String routeName,

        Integer stationId,

        String stationName,

        int noOfPassengers,

        LocalDateTime dateTime
) {
}
