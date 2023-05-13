package com.digitalnation.bussensus.service;

import com.digitalnation.bussensus.domain.Route;
import com.digitalnation.bussensus.repository.RouteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public Route save(Route route) {
        return routeRepository.save(route);
    }

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public Optional<Route> findById(Integer id) {
        return routeRepository.findById(id);
    }

    public List<Route> findByBus_Id(Integer busId) {
        return routeRepository.findByBus_BusId(busId);
    }
}
