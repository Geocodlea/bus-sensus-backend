package com.digitalnation.bussensus.controller;

import com.digitalnation.bussensus.domain.Station;
import com.digitalnation.bussensus.dto.StationDto;
import com.digitalnation.bussensus.dto.mapper.StationMapper;
import com.digitalnation.bussensus.exception.NotFoundException;
import com.digitalnation.bussensus.service.StationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class StationController {

    private final StationService stationService;

    private final StationMapper stationMapper;

    @PostMapping("/bussensus/stations")
    public ResponseEntity<StationDto> addStation(@Valid @RequestBody StationDto stationDto) {
        Station stationRequest = stationMapper.toEntity((stationDto));
        Station station = stationService.save(stationRequest);
        StationDto stationResponse = stationMapper.toDto(station);

        return ResponseEntity.status(HttpStatus.CREATED).body(stationResponse);
    }

    @GetMapping("/bussensus/stations")
    public ResponseEntity<List<StationDto>> getAllStations() {
        List<StationDto> stations = stationService.findAll()
                                                 .stream()
                                                 .map(stationMapper::toDto)
                                                 .collect(Collectors.toList());

        return ResponseEntity.ok(stations);
    }

    @GetMapping("/bussensus/stations/{id}")
    public ResponseEntity<StationDto> getStation(@PathVariable Integer id) {
        Station station = stationService.findById(id)
                                    .orElseThrow(() -> new NotFoundException("Station with id " + id + " not found"));

        StationDto stationResponse = stationMapper.toDto(station);
        return ResponseEntity.ok(stationResponse);
    }

    @GetMapping("/bussensus/buses/{bus_id}/routes/{route_id}/stations")
    public ResponseEntity<List<StationDto>> getStationsByRouteId(@PathVariable(name = "route_id") Integer id) {
        List<StationDto> stations = stationService.findByRoutes_Id(id)
                                              .stream()
                                              .map(stationMapper::toDto)
                                              .collect(Collectors.toList());

        return ResponseEntity.ok(stations);
    }
}
