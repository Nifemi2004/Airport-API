package com.airport.airport.repository;

import com.airport.airport.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByAirplaneId(long airplaneId);



}
