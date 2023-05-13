package com.digitalnation.bussensus.service;

import com.digitalnation.bussensus.domain.Bus;
import com.digitalnation.bussensus.domain.Report;
import com.digitalnation.bussensus.domain.Route;
import com.digitalnation.bussensus.domain.Station;
import com.digitalnation.bussensus.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ReportService.class)
@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    private Report report;

    @BeforeEach
    public void setup() {
        report = Report.builder()
                       .bus(new Bus())
                       .route(new Route())
                       .station(new Station())
                       .dateTime(LocalDateTime.parse("2023-05-02T18:40:23"))
                       .noOfPassengers(12)
                       .build();
    }

    @Test
    public void testSave() {
        when(reportRepository.save(report)).thenReturn(report);

        Report queryResult = reportService.save(report);

        assertNotNull(queryResult);
        assertSame(report, queryResult);
        verify(reportRepository).save(report);
    }

    @Test
    public void testFindAll() {
        Report report2 = Report.builder()
                               .bus(new Bus())
                               .route(new Route())
                               .station(new Station())
                               .dateTime(LocalDateTime.parse("2023-05-02T18:40:23"))
                               .noOfPassengers(12)
                               .build();

        List<Report> reportList = Arrays.asList(report, report2);
        when(reportRepository.findAll()).thenReturn(reportList);

        List<Report> queryResult = reportService.findAll();

        assertSame(reportList, queryResult);
        assertFalse(queryResult.isEmpty());
        assertEquals(2, reportList.size());
        verify(reportRepository).findAll();
    }

    @Test
    public void testFindById() {
        when(reportRepository.findById(1)).thenReturn(Optional.ofNullable(report));

        Optional<Report> queryResult = reportService.findById(1);

        assertFalse(queryResult.isEmpty());
        assertSame(report, queryResult.get());
        verify(reportRepository).findById(1);
    }
}
