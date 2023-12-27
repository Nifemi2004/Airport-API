package com.airport.airport.payload;

import com.airport.airport.utils.Grade;
import com.airport.airport.utils.TripType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDto {
    private Long id;
    private String passengerFullName;
    private String seatNumber;
    private Grade grade;
    @Enumerated
    private TripType tripType;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private long noOfAdults;
    private long noOfChildren;
    private long noOfInfants;
}
