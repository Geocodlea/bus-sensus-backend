package com.digitalnation.bussensus.dto.mapper;

import com.digitalnation.bussensus.domain.Report;
import com.digitalnation.bussensus.dto.ReportRequestDto;
import com.digitalnation.bussensus.dto.ReportResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportMapper {

    @Mapping(source = "busId", target = "bus.busId")
    @Mapping(source = "routeId", target = "route.routeId")
    @Mapping(source = "stationId", target = "station.stationId")
    Report toEntity(ReportRequestDto reportRequestDto);

    @Mapping(target = "busId", source = "bus.busId")
    @Mapping(target = "busName", source = "bus.name")
    @Mapping(target = "routeId", source = "route.routeId")
    @Mapping(target = "routeName", source = "route.name")
    @Mapping(target = "stationId", source = "station.stationId")
    @Mapping(target = "stationName", source = "station.name")
    ReportResponseDto toDto(Report report);
}
