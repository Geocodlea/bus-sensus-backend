package com.digitalnation.bussensus.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer routeId;

    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "route_station",
        joinColumns = @JoinColumn(name = "route_id"),
        inverseJoinColumns = @JoinColumn(name = "station_id"))
    private List<Station> stations = new ArrayList<>();

    public void addStation(Station station) {
        stations.add(station);
        station.getRoutes().add(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "routeId = " + routeId + ", " +
                "name = " + name + ")";
    }
}
