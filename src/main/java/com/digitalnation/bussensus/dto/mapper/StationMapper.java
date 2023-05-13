package com.digitalnation.bussensus.dto.mapper;

import com.digitalnation.bussensus.domain.Station;
import com.digitalnation.bussensus.dto.StationDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StationMapper {

    Station toEntity(StationDto stationDto);

    StationDto toDto(Station station);
}