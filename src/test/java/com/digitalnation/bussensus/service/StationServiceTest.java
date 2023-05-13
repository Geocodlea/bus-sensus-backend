package com.digitalnation.bussensus.service;

import com.digitalnation.bussensus.domain.Station;
import com.digitalnation.bussensus.repository.StationRepository;
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

@ContextConfiguration(classes = StationService.class)
@ExtendWith(MockitoExtension.class)
public class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private StationService stationService;

    private Station station;

    @BeforeEach
    public void setup() {
        station = Station.builder()
                       .name("Stad. Municipal")
                       .build();
    }

    @Test
    public void testSave() {
        when(stationRepository.save(station)).thenReturn(station);

        Station queryResult = stationService.save(station);

        assertNotNull(queryResult);
        assertSame(station, queryResult);
        verify(stationRepository).save(station);
    }

    @Test
    public void testFindAll() {
        Station station2 = Station.builder().name("Livada Postei").build();
        List<Station> stationList = Arrays.asList(station, station2);
        when(stationRepository.findAll()).thenReturn(stationList);

        List<Station> queryResult = stationService.findAll();

        assertSame(stationList, queryResult);
        assertFalse(queryResult.isEmpty());
        assertEquals(2, stationList.size());
        verify(stationRepository).findAll();
    }

    @Test
    public void testFindById() {
        when(stationRepository.findById(1)).thenReturn(Optional.ofNullable(station));

        Optional<Station> queryResult = stationService.findById(1);

        assertFalse(queryResult.isEmpty());
        assertSame(station, queryResult.get());
        verify(stationRepository).findById(1);
    }

    @Test
    public void testFindByBus_Id() {
        when(stationRepository.findByRoutes_RouteId(1)).thenReturn(Arrays.asList(station));

        List<Station> queryResult = stationService.findByRoutes_Id(1);

        assertTrue(queryResult.contains(station));
        assertFalse(queryResult.isEmpty());
        verify(stationRepository).findByRoutes_RouteId(1);
    }
}
