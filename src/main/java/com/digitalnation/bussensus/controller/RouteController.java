package com.digitalnation.bussensus.controller;

import com.digitalnation.bussensus.domain.Route;
import com.digitalnation.bussensus.dto.RouteDto;
import com.digitalnation.bussensus.dto.mapper.RouteMapper;
import com.digitalnation.bussensus.exception.NotFoundException;
import com.digitalnation.bussensus.service.RouteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class RouteController {

    private final RouteService routeService;

    private final RouteMapper routeMapper;

    @PostMapping("/bussensus/routes")
    public ResponseEntity<RouteDto> addRoute(@Valid @RequestBody RouteDto routeDto) {
        Route routeRequest = routeMapper.toEntity((routeDto));
        Route route = routeService.save(routeRequest);
        RouteDto routeResponse = routeMapper.toDto(route);

        return ResponseEntity.status(HttpStatus.CREATED).body(routeResponse);
    }

    @GetMapping("/bussensus/routes")
    public ResponseEntity<List<RouteDto>> getAllRoutes() {
        List<RouteDto> routeList = routeService.findAll()
                                           .stream()
                                           .map(routeMapper::toDto)
                                           .collect(Collectors.toList());

        return ResponseEntity.ok(routeList);
    }

    @GetMapping("/bussensus/routes/{id}")
    public ResponseEntity<RouteDto> getRoute(@PathVariable Integer id) {
        Route route = routeService.findById(id)
                              .orElseThrow(() -> new NotFoundException("Route with id " + id + " not found"));

        RouteDto routeResponse = routeMapper.toDto(route);
        return ResponseEntity.ok(routeResponse);
    }

    @GetMapping("bussensus/buses/{bus_id}/routes")
    public ResponseEntity<List<RouteDto>> getRoutesByBusId(@PathVariable(name = "bus_id") Integer id) {
        List<RouteDto> routes = routeService.findByBus_Id(id)
                                            .stream()
                                            .map(routeMapper::toDto)
                                            .collect(Collectors.toList());

        return ResponseEntity.ok(routes);
    }
}
