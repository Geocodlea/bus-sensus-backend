package com.digitalnation.bussensus.repository;

import com.digitalnation.bussensus.AbstractMySqlContainer;
import com.digitalnation.bussensus.domain.Bus;
import com.digitalnation.bussensus.domain.Route;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties =
        {"spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RouteRepositoryTest extends AbstractMySqlContainer {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private BusRepository busRepository;

    private Route route;

    @BeforeEach
    public void setup() {
        route = Route.builder()
                     .name("Stad. Municipal - Roman")
                     .build();
        routeRepository.save(route);
    }

    @Test
    public void testFindById() {
        Optional<Route> queryResult = routeRepository.findById(1);

        assertTrue(queryResult.isPresent());
        assertSame(route, queryResult.get());
        assertEquals(route.getRouteId(), queryResult.get().getRouteId());
    }

    @Test
    public void testFindAll() {
        List<Route> queryResult = routeRepository.findAll();

        assertFalse(queryResult.isEmpty());
        assertTrue(queryResult.contains(route));
    }

    @Test
    public void testDeleteById() {
        routeRepository.deleteById(1);

        Optional<Route> deletedShelter = routeRepository.findById(1);
        assertFalse(deletedShelter.isPresent());
    }

    @Test
    public void findByBus_Id() {
        Bus bus = Bus.builder().name("5").routes(new ArrayList<>()).build();
        bus.addRoute(route);
        busRepository.save(bus);

        List<Route> queryResult = routeRepository.findByBus_BusId(1);

        assertFalse(queryResult.isEmpty());
        assertTrue(queryResult.contains(route));
    }

}
