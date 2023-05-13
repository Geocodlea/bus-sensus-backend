package com.digitalnation.bussensus.service;

import com.digitalnation.bussensus.domain.Station;
import com.digitalnation.bussensus.repository.StationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StationService {

    private final StationRepository stationRepository;

    public Station save(Station station) {
        return stationRepository.save(station);
    }

    public List<Station> findAll() {
        return stationRepository.findAll();
    }

    public Optional<Station> findById(Integer id) {
        return stationRepository.findById(id);
    }

    public List<Station> findByRoutes_Id(Integer routeId) {
        return stationRepository.findByRoutes_RouteId(routeId);
    }

}
