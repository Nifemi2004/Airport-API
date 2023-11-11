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
@Table(name = "airplane")
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String aircraftRegistration;
    private String model;
    private long capacity;
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Flight> flights = new HashSet<>();
}
