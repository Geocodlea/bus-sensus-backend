package com.digitalnation.bussensus.dto.mapper;

import com.digitalnation.bussensus.domain.Route;
import com.digitalnation.bussensus.dto.RouteDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-11T14:20:57+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class RouteMapperImpl implements RouteMapper {

    @Override
    public Route toEntity(RouteDto routeDto) {
        if ( routeDto == null ) {
            return null;
        }

        Route.RouteBuilder route = Route.builder();

        route.routeId( routeDto.routeId() );
        route.name( routeDto.name() );

        return route.build();
    }

    @Override
    public RouteDto toDto(Route route) {
        if ( route == null ) {
            return null;
        }

        Integer routeId = null;
        String name = null;

        routeId = route.getRouteId();
        name = route.getName();

        RouteDto routeDto = new RouteDto( routeId, name );

        return routeDto;
    }
}
