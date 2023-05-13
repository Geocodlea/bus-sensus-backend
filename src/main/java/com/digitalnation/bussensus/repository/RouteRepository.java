package com.digitalnation.bussensus.repository;

import com.digitalnation.bussensus.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {

    List<Route> findByBus_BusId(Integer Id);

}
