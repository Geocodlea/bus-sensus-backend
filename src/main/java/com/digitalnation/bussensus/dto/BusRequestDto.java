package com.digitalnation.bussensus.dto;

import java.util.List;

public record BusRequestDto(

        Integer busId,

        String name,

        List<RouteDto> routes
) {
}
