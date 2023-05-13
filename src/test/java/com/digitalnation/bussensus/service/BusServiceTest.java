package com.digitalnation.bussensus.service;

import com.digitalnation.bussensus.domain.Bus;
import com.digitalnation.bussensus.domain.Route;
import com.digitalnation.bussensus.repository.BusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = BusService.class)
@ExtendWith(MockitoExtension.class)
public class BusServiceTest {

    @Mock
    private BusRepository busRepository;

    @InjectMocks
    private BusService busService;

    private Bus bus;

    private Route route;

    @BeforeEach
    public void setup() {
        route = Route.builder()
                     .name("Stad. Municipal - Roman")
                     .build();

        bus = Bus.builder()
                 .name("5")
                 .routes(new ArrayList<>(List.of(route)))
                 .build();
    }

    @Test
    public void testSave() {
        when(busRepository.save(bus)).thenReturn(bus);

        Bus queryResult = busService.save(bus);

        assertNotNull(queryResult);
        assertSame(bus, queryResult);
        assertTrue(queryResult.getRoutes().contains(route));
        verify(busRepository).save(bus);
    }

    @Test
    public void testFindAll() {
        Bus bus1 = Bus.builder().build();
        List<Bus> busList = Arrays.asList(bus, bus1);
        when(busRepository.findAll()).thenReturn(busList);

        List<Bus> queryResult = busService.findAll();

        assertSame(busList, queryResult);
        assertFalse(queryResult.isEmpty());
        assertEquals(2, busList.size());
        verify(busRepository).findAll();
    }

    @Test
    public void testFindById() {
        when(busRepository.findById(1)).thenReturn(Optional.ofNullable(bus));

        Optional<Bus> queryResult = busService.findById(1);

        assertFalse(queryResult.isEmpty());
        assertSame(bus, queryResult.get());
        verify(busRepository).findById(1);
    }
}
