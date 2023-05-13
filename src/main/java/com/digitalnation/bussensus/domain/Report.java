package com.digitalnation.bussensus.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer reportId;

    @OneToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @OneToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @OneToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @Min(value = 0)
    @Max(value = 100)
    private int noOfPassengers;

    LocalDateTime dateTime;
}
