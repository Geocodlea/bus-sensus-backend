package com.digitalnation.bussensus.controller;

import com.digitalnation.bussensus.domain.Bus;
import com.digitalnation.bussensus.dto.BusRequestDto;
import com.digitalnation.bussensus.dto.BusResponseDto;
import com.digitalnation.bussensus.dto.mapper.BusMapper;
import com.digitalnation.bussensus.exception.NotFoundException;
import com.digitalnation.bussensus.service.BusService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bussensus/buses")
@AllArgsConstructor
public class BusController {

    private final BusService busService;

    private final BusMapper busMapper;

    @PostMapping
    public ResponseEntity<BusResponseDto> addBus(@Valid @RequestBody BusRequestDto busRequestDto) {
        Bus busRequest = busMapper.toEntity((busRequestDto));
        Bus bus = busService.save(busRequest);
        BusResponseDto busResponse = busMapper.toDto(bus);

        return ResponseEntity.status(HttpStatus.CREATED).body(busResponse);
    }

    @GetMapping
    public ResponseEntity<List<BusResponseDto>> getAllBuses() {
        List<BusResponseDto> busList = busService.findAll()
                                                 .stream()
                                                 .map(busMapper::toDto)
                                                 .collect(Collectors.toList());

        return ResponseEntity.ok(busList);
    }

    @GetMapping("/{bus_id}")
    public ResponseEntity<BusResponseDto> getBus(@PathVariable(name = "bus_id") Integer id) {
        Bus bus = busService.findById(id)
                                     .orElseThrow(() -> new NotFoundException("Bus with id " + id + " not found"));

        BusResponseDto busResponse = busMapper.toDto(bus);
        return ResponseEntity.ok(busResponse);
    }
}
