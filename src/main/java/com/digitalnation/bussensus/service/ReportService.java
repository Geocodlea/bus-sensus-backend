package com.digitalnation.bussensus.service;

import com.digitalnation.bussensus.domain.Report;
import com.digitalnation.bussensus.repository.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public Report save(Report report) {
        report.setDateTime(LocalDateTime.now());
        return reportRepository.save(report);
    }

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public Optional<Report> findById(Integer id) {
        return reportRepository.findById(id);
    }
}
