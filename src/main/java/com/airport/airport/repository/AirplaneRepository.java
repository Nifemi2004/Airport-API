package com.airport.airport.repository;

import com.airport.airport.entity.Airplane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    List<Airplane> findByAirlineId(long airlineId);

    @Query("SELECT a FROM Airplane a WHERE a.airline IS NOT NULL")
    List<Airplane> findAllAirplanesWithAirlines();

    @Query("SELECT a.capacity FROM Airplane a WHERE a.id = :airplaneId")
    Long getTotalSeatsByAirplaneId(@Param("airplaneId") Long airplaneId);
}
