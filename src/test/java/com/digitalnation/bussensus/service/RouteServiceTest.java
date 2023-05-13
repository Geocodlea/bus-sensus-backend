package com.digitalnation.bussensus.service;

import com.digitalnation.bussensus.domain.Route;
import com.digitalnation.bussensus.repository.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = RouteService.class)
@ExtendWith(MockitoExtension.class)
public class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteService routeService;

    private Route route;

    @BeforeEach
    public void setup() {
        route = Route.builder()
                     .name("Stad. Municipal - Roman")
                     .build();
    }

    @Test
    public void testSave() {
        when(routeRepository.save(route)).thenReturn(route);

        Route queryResult = routeService.save(route);

        assertNotNull(queryResult);
        assertSame(route, queryResult);
        verify(routeRepository).save(route);
    }

    @Test
    public void testFindAll() {
        Route route1 = Route.builder().name("Livada Postei - Roman").build();
        List<Route> routeList = Arrays.asList(route, route1);
        when(routeRepository.findAll()).thenReturn(routeList);

        List<Route> queryResult = routeService.findAll();

        assertSame(routeList, queryResult);
        assertFalse(queryResult.isEmpty());
        assertEquals(2, routeList.size());
        verify(routeRepository).findAll();
    }

    @Test
    public void testFindById() {
        when(routeRepository.findById(1)).thenReturn(Optional.ofNullable(route));

        Optional<Route> queryResult = routeService.findById(1);

        assertFalse(queryResult.isEmpty());
        assertSame(route, queryResult.get());
        verify(routeRepository).findById(1);
    }

    @Test
    public void testFindByBus_Id() {
        when(routeRepository.findByBus_BusId(1)).thenReturn(Arrays.asList(route));

        List<Route> queryResult = routeService.findByBus_Id(1);

        assertTrue(queryResult.contains(route));
        assertFalse(queryResult.isEmpty());
        verify(routeRepository).findByBus_BusId(1);
    }
}
