package com.airport.airport.payload;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FlightDto {

    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private Long economyPrice;
    private Long businessPrice;
    private Long firstPrice;
    private String status;
}
