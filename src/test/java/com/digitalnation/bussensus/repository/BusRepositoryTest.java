package com.digitalnation.bussensus.repository;

import com.digitalnation.bussensus.AbstractMySqlContainer;
import com.digitalnation.bussensus.domain.Bus;
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
public class BusRepositoryTest extends AbstractMySqlContainer {

    @Autowired
    private BusRepository busRepository;

    private Bus bus;

    @BeforeEach
    public void setup() {
        bus = Bus.builder()
                 .name("5")
                 .build();

        busRepository.save(bus);
    }

    @Test
    public void testFindById() {
        Optional<Bus> queryResult = busRepository.findById(1);

        assertTrue(queryResult.isPresent());
        assertSame(bus, queryResult.get());
        assertEquals(bus.getBusId(), queryResult.get().getBusId());
    }

    @Test
    public void testFindAll() {
        List<Bus> queryResult = busRepository.findAll();

        assertFalse(queryResult.isEmpty());
        assertTrue(queryResult.contains(bus));
    }

    @Test
    public void testDeleteById() {
        busRepository.deleteById(1);

        Optional<Bus> deletedShelter = busRepository.findById(1);
        assertFalse(deletedShelter.isPresent());
    }
}
