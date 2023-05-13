package com.digitalnation.bussensus.dto.mapper;

import com.digitalnation.bussensus.domain.Bus;
import com.digitalnation.bussensus.domain.Route;
import com.digitalnation.bussensus.dto.BusRequestDto;
import com.digitalnation.bussensus.dto.BusResponseDto;
import com.digitalnation.bussensus.dto.RouteDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-11T14:20:58+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class BusMapperImpl implements BusMapper {

    @Override
    public Bus toEntity(BusRequestDto busRequestDto) {
        if ( busRequestDto == null ) {
            return null;
        }

        Bus.BusBuilder bus = Bus.builder();

        bus.busId( busRequestDto.busId() );
        bus.name( busRequestDto.name() );
        bus.routes( routeDtoListToRouteList( busRequestDto.routes() ) );

        return bus.build();
    }

    @Override
    public BusResponseDto toDto(Bus bus) {
        if ( bus == null ) {
            return null;
        }

        Integer busId = null;
        String name = null;

        busId = bus.getBusId();
        name = bus.getName();

        BusResponseDto busResponseDto = new BusResponseDto( busId, name );

        return busResponseDto;
    }

    protected Route routeDtoToRoute(RouteDto routeDto) {
        if ( routeDto == null ) {
            return null;
        }

        Route.RouteBuilder route = Route.builder();

        route.routeId( routeDto.routeId() );
        route.name( routeDto.name() );

        return route.build();
    }

    protected List<Route> routeDtoListToRouteList(List<RouteDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Route> list1 = new ArrayList<Route>( list.size() );
        for ( RouteDto routeDto : list ) {
            list1.add( routeDtoToRoute( routeDto ) );
        }

        return list1;
    }
}
