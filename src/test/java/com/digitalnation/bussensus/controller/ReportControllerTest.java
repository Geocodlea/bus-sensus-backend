package com.digitalnation.bussensus.controller;

import com.digitalnation.bussensus.domain.Bus;
import com.digitalnation.bussensus.domain.Report;
import com.digitalnation.bussensus.domain.Route;
import com.digitalnation.bussensus.domain.Station;
import com.digitalnation.bussensus.exception.NotFoundException;
import com.digitalnation.bussensus.repository.BusRepository;
import com.digitalnation.bussensus.repository.RouteRepository;
import com.digitalnation.bussensus.repository.StationRepository;
import com.digitalnation.bussensus.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ReportControllerTest {

    @MockBean
    private ReportService reportService;

    @Autowired
    private MockMvc mockMvc;

    private Report report;

    private Bus bus;

    private Station station;

    private Route route;
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private StationRepository stationRepository;

    @BeforeEach
    public void setup() {
        bus = Bus.builder()
                     .busId(1)
                     .name("5")
                     .build();

        route = Route.builder()
                           .routeId(1)
                           .name("Stad. Municipal - Roman")
                           .build();

        station = Station.builder().stationId(1).name("Stad. Municipal").build();

        report = Report.builder()
                       .reportId(1)
                       .bus(bus)
                       .route(route)
                       .station(station)
                       .noOfPassengers(12)
                       .dateTime(LocalDateTime.parse("2023-05-02T18:40:23"))
                       .build();
    }

    @Test
    public void testAddReport() throws Exception {
        Report reportToSave = Report.builder()
                                    .bus(bus)
                                    .route(route)
                                    .station(station)
                                    .noOfPassengers(12)
                                    .dateTime(LocalDateTime.parse("2023-05-02T18:40:23"))
                                    .build();

        when(reportService.save(any())).thenReturn(report);

        mockMvc.perform(post("/bussensus/reports")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(asJsonString(reportToSave)))
               .andExpect(status().isCreated())

               .andExpect(jsonPath("$.reportId", is(1)))
               .andExpect(jsonPath("$.busId", is(1)))
               .andExpect(jsonPath("$.routeId", is(1)))
               .andExpect(jsonPath("$.stationId", is(1)))
               .andExpect(jsonPath("$.noOfPassengers", is(12)))
               .andExpect(jsonPath("$.dateTime", is("2023-05-02T18:40:23")));
    }

    @Test
    public void testGetAllReports() throws Exception {
        when(reportService.findAll()).thenReturn(List.of(report));

        mockMvc.perform(get("/bussensus/reports"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))

               .andExpect(jsonPath("$[0].reportId", is(1)))
               .andExpect(jsonPath("$[0].busId", is(1)))
               .andExpect(jsonPath("$[0].routeId", is(1)))
               .andExpect(jsonPath("$[0].stationId", is(1)))
               .andExpect(jsonPath("$[0].noOfPassengers", is(12)))
               .andExpect(jsonPath("$[0].dateTime", is("2023-05-02T18:40:23")));
    }

    @Test
    public void testGetReportById() throws Exception {
        when(reportService.findById(1)).thenReturn(Optional.ofNullable(report));

        mockMvc.perform(get("/bussensus/reports/{id}", 1))
               .andExpect(status().isOk())

               .andExpect(jsonPath("$.reportId", is(1)))
               .andExpect(jsonPath("$.busId", is(1)))
               .andExpect(jsonPath("$.routeId", is(1)))
               .andExpect(jsonPath("$.stationId", is(1)))
               .andExpect(jsonPath("$.noOfPassengers", is(12)))
               .andExpect(jsonPath("$.dateTime", is("2023-05-02T18:40:23")));
    }

    @Test
    public void testGetReportByIdNotFound() throws Exception {
        when(reportService.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/bussensus/reports/{id}", 1))
               .andExpect(status().isNotFound())

               .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof NotFoundException))
               .andExpect(result -> Assertions.assertEquals("Report with id 1 not found",
                       Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }


    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
