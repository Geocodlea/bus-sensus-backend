package com.digitalnation.bussensus.repository;

import com.digitalnation.bussensus.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}