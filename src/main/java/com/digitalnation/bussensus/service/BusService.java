package com.digitalnation.bussensus.service;

import com.digitalnation.bussensus.domain.Bus;
import com.digitalnation.bussensus.repository.BusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BusService {

    private final BusRepository busRepository;

    public Bus save(Bus bus) {
        return busRepository.save(bus);
    }

    public List<Bus> findAll() {
        return busRepository.findAll();
    }

    public Optional<Bus> findById(Integer id) {
        return busRepository.findById(id);
    }
}
