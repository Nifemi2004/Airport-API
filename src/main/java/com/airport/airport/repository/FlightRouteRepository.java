package com.airport.airport.repository;

import com.airport.airport.entity.FlightRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRouteRepository extends JpaRepository<FlightRoute, Long> {

    List<FlightRoute> findByAirlineId(long airlineId);

    @Query("SELECT fr.destination FROM FlightRoute fr WHERE fr.origin = :origin")
    List<String> findDestinationByOrigin(String origin);
}
