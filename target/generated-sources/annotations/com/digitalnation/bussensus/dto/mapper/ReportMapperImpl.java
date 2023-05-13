package com.digitalnation.bussensus.dto.mapper;

import com.digitalnation.bussensus.domain.Bus;
import com.digitalnation.bussensus.domain.Report;
import com.digitalnation.bussensus.domain.Route;
import com.digitalnation.bussensus.domain.Station;
import com.digitalnation.bussensus.dto.ReportRequestDto;
import com.digitalnation.bussensus.dto.ReportResponseDto;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-11T14:20:58+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ReportMapperImpl implements ReportMapper {

    @Override
    public Report toEntity(ReportRequestDto reportRequestDto) {
        if ( reportRequestDto == null ) {
            return null;
        }

        Report.ReportBuilder report = Report.builder();

        report.bus( reportRequestDtoToBus( reportRequestDto ) );
        report.route( reportRequestDtoToRoute( reportRequestDto ) );
        report.station( reportRequestDtoToStation( reportRequestDto ) );
        report.reportId( reportRequestDto.reportId() );
        report.noOfPassengers( reportRequestDto.noOfPassengers() );
        report.dateTime( reportRequestDto.dateTime() );

        return report.build();
    }

    @Override
    public ReportResponseDto toDto(Report report) {
        if ( report == null ) {
            return null;
        }

        Integer busId = null;
        String busName = null;
        Integer routeId = null;
        String routeName = null;
        Integer stationId = null;
        String stationName = null;
        Integer reportId = null;
        int noOfPassengers = 0;
        LocalDateTime dateTime = null;

        busId = reportBusBusId( report );
        busName = reportBusName( report );
        routeId = reportRouteRouteId( report );
        routeName = reportRouteName( report );
        stationId = reportStationStationId( report );
        stationName = reportStationName( report );
        reportId = report.getReportId();
        noOfPassengers = report.getNoOfPassengers();
        dateTime = report.getDateTime();

        ReportResponseDto reportResponseDto = new ReportResponseDto( reportId, busId, busName, routeId, routeName, stationId, stationName, noOfPassengers, dateTime );

        return reportResponseDto;
    }

    protected Bus reportRequestDtoToBus(ReportRequestDto reportRequestDto) {
        if ( reportRequestDto == null ) {
            return null;
        }

        Bus.BusBuilder bus = Bus.builder();

        bus.busId( reportRequestDto.busId() );

        return bus.build();
    }

    protected Route reportRequestDtoToRoute(ReportRequestDto reportRequestDto) {
        if ( reportRequestDto == null ) {
            return null;
        }

        Route.RouteBuilder route = Route.builder();

        route.routeId( reportRequestDto.routeId() );

        return route.build();
    }

    protected Station reportRequestDtoToStation(ReportRequestDto reportRequestDto) {
        if ( reportRequestDto == null ) {
            return null;
        }

        Station.StationBuilder station = Station.builder();

        station.stationId( reportRequestDto.stationId() );

        return station.build();
    }

    private Integer reportBusBusId(Report report) {
        if ( report == null ) {
            return null;
        }
        Bus bus = report.getBus();
        if ( bus == null ) {
            return null;
        }
        Integer busId = bus.getBusId();
        if ( busId == null ) {
            return null;
        }
        return busId;
    }

    private String reportBusName(Report report) {
        if ( report == null ) {
            return null;
        }
        Bus bus = report.getBus();
        if ( bus == null ) {
            return null;
        }
        String name = bus.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Integer reportRouteRouteId(Report report) {
        if ( report == null ) {
            return null;
        }
        Route route = report.getRoute();
        if ( route == null ) {
            return null;
        }
        Integer routeId = route.getRouteId();
        if ( routeId == null ) {
            return null;
        }
        return routeId;
    }

    private String reportRouteName(Report report) {
        if ( report == null ) {
            return null;
        }
        Route route = report.getRoute();
        if ( route == null ) {
            return null;
        }
        String name = route.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Integer reportStationStationId(Report report) {
        if ( report == null ) {
            return null;
        }
        Station station = report.getStation();
        if ( station == null ) {
            return null;
        }
        Integer stationId = station.getStationId();
        if ( stationId == null ) {
            return null;
        }
        return stationId;
    }

    private String reportStationName(Report report) {
        if ( report == null ) {
            return null;
        }
        Station station = report.getStation();
        if ( station == null ) {
            return null;
        }
        String name = station.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
