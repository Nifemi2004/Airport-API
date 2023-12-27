package com.airport.airport.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Airline", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String headquarters;

    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL, orphanRemoval = true   )
    private Set<Airplane> airplanes = new HashSet<>();

    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL, orphanRemoval = true   )
    private Set<FlightRoute> flightRoutes  = new HashSet<>();
}
