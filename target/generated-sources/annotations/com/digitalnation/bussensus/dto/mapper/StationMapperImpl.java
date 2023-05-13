package com.digitalnation.bussensus.dto.mapper;

import com.digitalnation.bussensus.domain.Station;
import com.digitalnation.bussensus.dto.StationDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-11T14:20:58+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class StationMapperImpl implements StationMapper {

    @Override
    public Station toEntity(StationDto stationDto) {
        if ( stationDto == null ) {
            return null;
        }

        Station.StationBuilder station = Station.builder();

        station.stationId( stationDto.stationId() );
        station.name( stationDto.name() );

        return station.build();
    }

    @Override
    public StationDto toDto(Station station) {
        if ( station == null ) {
            return null;
        }

        Integer stationId = null;
        String name = null;

        stationId = station.getStationId();
        name = station.getName();

        StationDto stationDto = new StationDto( stationId, name );

        return stationDto;
    }
}
