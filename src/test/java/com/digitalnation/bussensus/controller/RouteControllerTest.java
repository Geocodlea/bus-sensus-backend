package com.digitalnation.bussensus.controller;

import com.digitalnation.bussensus.domain.Route;
import com.digitalnation.bussensus.exception.NotFoundException;
import com.digitalnation.bussensus.service.RouteService;
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
public class RouteControllerTest {

    @MockBean
    private RouteService routeService;

    @Autowired
    private MockMvc mockMvc;

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
    }

    @Test
    public void testAddRoute() throws Exception {
        Route routeToSave = Route.builder()
                           .name("Stad. Municipal - Roman")
                           .build();

        when(routeService.save(any())).thenReturn(route1);

        mockMvc.perform(post("/bussensus/routes")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(asJsonString(routeToSave)))
               .andExpect(status().isCreated())

               .andExpect(jsonPath("$.routeId", is(1)))
               .andExpect(jsonPath("$.name", is("Stad. Municipal - Roman")));
    }

    @Test
    public void testGetAllRoutes() throws Exception {
        when(routeService.findAll()).thenReturn(List.of(route1, route2));

        mockMvc.perform(get("/bussensus/routes"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))

               .andExpect(jsonPath("$[0].routeId", is(1)))
               .andExpect(jsonPath("$[0].name", is("Stad. Municipal - Roman")))
               .andExpect(jsonPath("$[1].routeId", is(2)))
               .andExpect(jsonPath("$[1].name", is("Stad. Municipal - Magurele")));

    }

    @Test
    public void testGetRouteById() throws Exception {
        when(routeService.findById(1)).thenReturn(Optional.ofNullable(route1));

        mockMvc.perform(get("/bussensus/routes/{id}", 1))
               .andExpect(status().isOk())

               .andExpect(jsonPath("$.routeId", is(1)))
               .andExpect(jsonPath("$.name", is("Stad. Municipal - Roman")));
    }

    @Test
    public void testGetRouteByIdNotFound() throws Exception {
        when(routeService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/bussensus/routes/{id}", 1))
               .andExpect(status().isNotFound())

               .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
               .andExpect(result -> assertEquals("Route with id 1 not found",
                       Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void testGetRouteByBus_Id() throws Exception {
        when(routeService.findByBus_Id(1)).thenReturn(List.of(route1, route2));

        mockMvc.perform(get("/bussensus/buses/{bus_id}/routes", 1))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))

               .andExpect(jsonPath("$[0].routeId", is(1)))
               .andExpect(jsonPath("$[0].name", is("Stad. Municipal - Roman")))
               .andExpect(jsonPath("$[1].routeId", is(2)))
               .andExpect(jsonPath("$[1].name", is("Stad. Municipal - Magurele")));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
