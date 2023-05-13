package com.digitalnation.bussensus.controller;

import com.digitalnation.bussensus.domain.Station;
import com.digitalnation.bussensus.exception.NotFoundException;
import com.digitalnation.bussensus.service.StationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class StationControllerTest {

    @MockBean
    private StationService stationService;

    @Autowired
    private MockMvc mockMvc;

    private Station station1;

    private Station station2;

    @BeforeEach
    public void setup() {
        station1 = Station.builder()
                        .stationId(1)
                        .name("Stad. Municipal")
                        .build();

        station2 = Station.builder()
                        .stationId(2)
                        .name("Magurele")
                        .build();
    }

    @Test
    public void testAddStation() throws Exception {
        Station stationToSave = Station.builder()
                                 .name("Stad. Municipal")
                                 .build();

        when(stationService.save(any())).thenReturn(station1);

        mockMvc.perform(post("/bussensus/stations")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(asJsonString(stationToSave)))
               .andExpect(status().isCreated())

               .andExpect(jsonPath("$.stationId", is(1)))
               .andExpect(jsonPath("$.name", is("Stad. Municipal")));
    }

    @Test
    public void testGetAllStations() throws Exception {
        when(stationService.findAll()).thenReturn(List.of(station1, station2));

        mockMvc.perform(get("/bussensus/stations"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))

               .andExpect(jsonPath("$[0].stationId", is(1)))
               .andExpect(jsonPath("$[0].name", is("Stad. Municipal")))
               .andExpect(jsonPath("$[1].stationId", is(2)))
               .andExpect(jsonPath("$[1].name", is("Magurele")));

    }

    @Test
    public void testGetStationById() throws Exception {
        when(stationService.findById(1)).thenReturn(Optional.ofNullable(station1));

        mockMvc.perform(get("/bussensus/stations/{id}", 1))
               .andExpect(status().isOk())

               .andExpect(jsonPath("$.stationId", is(1)))
               .andExpect(jsonPath("$.name", is("Stad. Municipal")));
    }

    @Test
    public void testGetStationByIdNotFound() throws Exception {
        when(stationService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/bussensus/stations/{id}", 1))
               .andExpect(status().isNotFound())

               .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
               .andExpect(result -> assertEquals("Station with id 1 not found",
                       Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testGetStationByRoute_Id() throws Exception {
        when(stationService.findByRoutes_Id(1)).thenReturn(List.of(station1, station2));

        mockMvc.perform(get("/bussensus/buses/{bus_id}/routes/{route_id}/stations", 1,1))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))

               .andExpect(jsonPath("$[0].stationId", is(1)))
               .andExpect(jsonPath("$[0].name", is("Stad. Municipal")))
               .andExpect(jsonPath("$[1].stationId", is(2)))
               .andExpect(jsonPath("$[1].name", is("Magurele")));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
