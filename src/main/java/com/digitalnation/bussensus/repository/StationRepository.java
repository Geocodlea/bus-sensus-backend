package com.digitalnation.bussensus.repository;

import com.digitalnation.bussensus.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {

    List<Station> findByRoutes_RouteId(Integer id);
}