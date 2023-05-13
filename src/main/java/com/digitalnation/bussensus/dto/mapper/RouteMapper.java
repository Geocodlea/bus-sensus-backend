package com.digitalnation.bussensus.dto.mapper;

import com.digitalnation.bussensus.domain.Route;
import com.digitalnation.bussensus.dto.RouteDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RouteMapper {

    Route toEntity(RouteDto routeDto);

    RouteDto toDto(Route route);
}