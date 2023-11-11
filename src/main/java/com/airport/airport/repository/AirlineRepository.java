package com.airport.airport.repository;

import com.airport.airport.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
}
