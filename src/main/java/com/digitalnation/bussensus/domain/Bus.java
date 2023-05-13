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
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer busId;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Route> routes = new ArrayList<>();

    public void addRoute(Route route) {
    routes.add(route);
    route.setBus(this);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "busId = " + busId + ", " +
                "name = " + name + ")";
    }
}
