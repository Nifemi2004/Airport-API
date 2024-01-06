package com.airport.airport.repository;

import com.airport.airport.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByAirplaneId(long airplaneId);

    @Query("SELECT f FROM Flight f JOIN f.airplane a WHERE a.airline.id  = :airlineId ")
    List<Flight> findAllFlightWithTheSameAirlines(@Param("airlineId") Long airlineId);

    @Query("SELECT f FROM Flight f JOIN f.airplane a WHERE a.airline.id = :airlineId " +
            "AND f.origin = :origin AND f.destination = :destination " +
            "AND (:departureDate IS NULL OR f.departureDate = :departureDate OR f.departureDate IS NULL) " +
            "AND (:arrivalDate IS NULL OR f.arrivalDate = :arrivalDate OR f.arrivalDate IS NULL)")
    List<Flight> findFlightsByAirlineAndConditions(
            @Param("airlineId") Long airlineId,
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("departureDate") String departureDate,
            @Param("arrivalDate") String arrivalDate);

    @Query("SELECT f FROM Flight f JOIN f.airplane a WHERE a.airline.id  = :airlineId " +
            "AND f.origin = :origin AND f.destination = :destination " +
            "AND (f.departureDate, f.economyPrice, f.id) IN " +
            "(SELECT f2.departureDate, MIN(f2.economyPrice), MIN(f2.id) FROM Flight f2 GROUP BY f2.departureDate)")
    List<Flight> findFlightsWithLowestPricePerDay(@Param("airlineId") Long airlineId,
                                                  @Param("origin") String origin,
                                                  @Param("destination") String destination);
}
