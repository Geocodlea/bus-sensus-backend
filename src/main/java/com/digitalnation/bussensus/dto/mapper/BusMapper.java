package com.digitalnation.bussensus.dto.mapper;

import com.digitalnation.bussensus.domain.Bus;
import com.digitalnation.bussensus.dto.BusRequestDto;
import com.digitalnation.bussensus.dto.BusResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BusMapper {

    Bus toEntity(BusRequestDto busRequestDto);

    BusResponseDto toDto(Bus bus);
}
