package com.digitalnation.bussensus.dto.mapper;

import com.digitalnation.bussensus.domain.Report;
import com.digitalnation.bussensus.dto.ReportRequestDto;
import com.digitalnation.bussensus.dto.ReportResponseDto;
import com.digitalnation.bussensus.repository.BusRepository;
import com.digitalnation.bussensus.repository.ReportRepository;
import com.digitalnation.bussensus.repository.RouteRepository;
import com.digitalnation.bussensus.repository.StationRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Primary
@AllArgsConstructor
public class ReportMapperImplementation implements ReportMapper{

    private final ReportRepository reportRepository;

    private final BusRepository busRepository;

    private final RouteRepository routeRepository;

    private final StationRepository stationRepository;

    @Override
    public Report toEntity(ReportRequestDto reportRequestDto) {
        if ( reportRequestDto == null ) {
            return null;
        }

        Report.ReportBuilder report = Report.builder();

        report.bus(busRepository.getReferenceById(reportRequestDto.busId()));
        report.route(routeRepository.getReferenceById(reportRequestDto.routeId()));
        report.station(stationRepository.getReferenceById(reportRequestDto.stationId()));
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

        Integer busId;
        String busName;
        Integer routeId;
        String routeName;
        Integer stationId;
        String stationName;
        Integer reportId;
        int noOfPassengers;
        LocalDateTime dateTime;

        busId = report.getBus().getBusId();
        busName = busIdToBusName(busId);
        routeId = report.getRoute().getRouteId();
        routeName = routeIdToRouteName(routeId);
        stationId = report.getStation().getStationId();
        stationName = stationIdToStationName(stationId);
        reportId = report.getReportId();
        noOfPassengers = report.getNoOfPassengers();
        dateTime = report.getDateTime();

        return new ReportResponseDto( reportId, busId, busName, routeId, routeName, stationId, stationName, noOfPassengers, dateTime );
    }

    private String busIdToBusName(Integer busId) {
        return busRepository.getReferenceById(busId).getName();
    }

    private String stationIdToStationName(Integer stationId) {
        return stationRepository.getReferenceById(stationId).getName();
    }

    private String routeIdToRouteName(Integer routeId) {
        return routeRepository.getReferenceById(routeId).getName();
    }
}
