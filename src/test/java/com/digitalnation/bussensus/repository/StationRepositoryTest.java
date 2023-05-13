package com.digitalnation.bussensus.repository;

import com.digitalnation.bussensus.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties =
        {"spring.flyway.enabled=false",
         "spring.jpa.hibernate.ddl-auto=create"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    private Station station;

    @BeforeEach
    public void setup() {
        station = Station.builder()
                     .name("Livada Postei")
                     .build();

        stationRepository.save(station);
    }

    @Test
    public void testFindById() {
        Optional<Station> queryResult = stationRepository.findById(1);

        assertTrue(queryResult.isPresent());
        assertSame(station, queryResult.get());
        assertEquals(station.getStationId(), queryResult.get().getStationId());
    }

    @Test
    public void testFindAll() {
        List<Station> queryResult = stationRepository.findAll();

        assertFalse(queryResult.isEmpty());
        assertTrue(queryResult.contains(station));
    }

    @Test
    public void testDeleteById() {
        stationRepository.deleteById(1);

        Optional<Station> deletedShelter = stationRepository.findById(1);
        assertFalse(deletedShelter.isPresent());
    }
}
