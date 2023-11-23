package com.airport.airport.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightDto {

    private String flightNumber;
    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private String status;
}
