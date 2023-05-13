package com.digitalnation.bussensus.repository;

import com.digitalnation.bussensus.domain.Bus;
import com.digitalnation.bussensus.domain.Report;
import com.digitalnation.bussensus.domain.Route;
import com.digitalnation.bussensus.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties =
        {"spring.flyway.enabled=false",
         "spring.jpa.hibernate.ddl-auto=create"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private BusRepository busRepository;

    private Report report;

    private Bus bus;

    private Route route;

    private Station station;

    @BeforeEach
    public void setup() {
        station = createStation();
        route = createRoute();
        bus = createBus();
        busRepository.save(bus);

        report = Report.builder()
                       .bus(bus)
                       .route(route)
                       .station(station)
                       .dateTime(LocalDateTime.now())
                       .noOfPassengers(12)
                       .build();

        reportRepository.save(report);
    }

    @Test
    public void testFindById() {
        Optional<Report> queryResult = reportRepository.findById(1);

        assertTrue(queryResult.isPresent());
        assertSame(report, queryResult.get());
        assertEquals(report.getReportId(), queryResult.get().getReportId());
    }

    @Test
    public void testFindAll() {
        List<Report> queryResult = reportRepository.findAll();

        assertFalse(queryResult.isEmpty());
        assertTrue(queryResult.contains(report));
    }

    @Test
    public void testDeleteById() {
        reportRepository.deleteById(1);

        Optional<Report> deletedShelter = reportRepository.findById(1);
        assertFalse(deletedShelter.isPresent());
    }

    private  Bus createBus() {
        return Bus.builder()
                  .busId(1)
                  .name("5")
                  .routes(Arrays.asList(route))
                  .build();
    }

    private  Route createRoute() {
        return Route.builder()
                    .routeId(1)
                    .name("Stad. Municipal - Roman")
                    .stations(Arrays.asList(station))
                    .build();
    }

    private  Station createStation() {
        return Station.builder()
                      .stationId(1)
                      .name("Roman")
                      .build();
    }
}
