package com.digitalnation.bussensus.controller;

import com.digitalnation.bussensus.domain.Bus;
import com.digitalnation.bussensus.domain.Route;
import com.digitalnation.bussensus.exception.NotFoundException;
import com.digitalnation.bussensus.service.BusService;
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
public class BusControllerTest {

    @MockBean
    private BusService busService;

    @Autowired
    private MockMvc mockMvc;

    private Bus bus;

    private Route route1;

    private Route route2;

    @BeforeEach
    public void setup() {
        route1 = Route.builder()
                      .routeId(1)
                      .name("Stad. Municipal - Roman")
                      .build();

        route2 = Route.builder()
                      .routeId(2)
                      .name("Stad. Municipal - Magurele")
                      .build();

        bus = Bus.builder().busId(1).name("5").routes(List.of(route1, route2)).build();
    }

    @Test
    public void testAddBus() throws Exception {
        Bus busToSave = Bus.builder()
                           .name("5")
                           .routes(List.of(route1, route2))
                           .build();

        when(busService.save(any())).thenReturn(bus);

        mockMvc.perform(post("/bussensus/buses")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(asJsonString(busToSave)))
               .andExpect(status().isCreated())

               .andExpect(jsonPath("$.busId", is(1)))
               .andExpect(jsonPath("$.name", is("5")))
               .andExpect(jsonPath("$.routes.[0].routeId", is(1)))
               .andExpect(jsonPath("$.routes.[1].routeId", is(2)))
               .andExpect(jsonPath("$.routes", hasSize(2)));
    }

    @Test
    public void testGetAllBuses() throws Exception {
        when(busService.findAll()).thenReturn(List.of(bus));

        mockMvc.perform(get("/bussensus/buses"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))

               .andExpect(jsonPath("$[0].busId", is(1)))
               .andExpect(jsonPath("$[0].name", is("5")));
    }

    @Test
    public void testGetBusById() throws Exception {
        when(busService.findById(1)).thenReturn(Optional.ofNullable(bus));

        mockMvc.perform(get("/bussensus/buses/{bus_id}", 1))
               .andExpect(status().isOk())

               .andExpect(jsonPath("$.busId", is(1)))
               .andExpect(jsonPath("$.name", is("5")));
    }

    @Test
    public void testGetBusByIdNotFound() throws Exception {
        when(busService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/bussensus/buses/{bus_id}", 1))
               .andExpect(status().isNotFound())

               .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
               .andExpect(result -> assertEquals("Bus with id 1 not found",
                       Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
