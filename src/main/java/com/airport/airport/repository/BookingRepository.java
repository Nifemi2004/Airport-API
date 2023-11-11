package com.airport.airport.repository;

import com.airport.airport.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByFlightId(long flightId);

    @Query("SELECT b.seatNumber FROM Booking b WHERE b.flight.id = :flightId ORDER BY b.seatNumber DESC")
    List<String> findLastAssignedSeatByFlightId(@Param("flightId") Long flightId);
}
