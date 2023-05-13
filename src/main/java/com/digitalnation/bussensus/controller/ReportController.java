package com.digitalnation.bussensus.controller;

import com.digitalnation.bussensus.domain.Report;
import com.digitalnation.bussensus.dto.ReportRequestDto;
import com.digitalnation.bussensus.dto.ReportResponseDto;
import com.digitalnation.bussensus.dto.mapper.ReportMapper;
import com.digitalnation.bussensus.exception.NotFoundException;
import com.digitalnation.bussensus.service.ReportService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/bussensus/reports")
public class ReportController {

    private final ReportService reportService;

    private final ReportMapper reportMapper;

    @PostMapping
    public ResponseEntity<ReportResponseDto> addReport(@Valid @RequestBody ReportRequestDto reportRequestDto) {
        Report reportRequest = reportMapper.toEntity((reportRequestDto));
        Report report = reportService.save(reportRequest);
        ReportResponseDto reportResponse = reportMapper.toDto(report);

        return ResponseEntity.status(HttpStatus.CREATED).body(reportResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReportResponseDto>> getAllReports() {
        List<ReportResponseDto> routeList = reportService.findAll()
                                                            .stream()
                                                            .map(reportMapper::toDto)
                                                            .collect(Collectors.toList());

        return ResponseEntity.ok(routeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponseDto> getReport(@PathVariable Integer id) {
        Report report = reportService.findById(id)
                                   .orElseThrow(() -> new NotFoundException("Report with id " + id + " not found"));

        ReportResponseDto reportResponse = reportMapper.toDto(report);
        return ResponseEntity.ok(reportResponse);
    }
}
