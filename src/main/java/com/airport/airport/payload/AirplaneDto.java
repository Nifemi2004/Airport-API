package com.airport.airport.payload;

import lombok.Data;


@Data
public class AirplaneDto {
    private Long id;
    private String aircraftRegistration;
    private String model;
    private Long capacity;
    private String code;
    private Long airlineId;
}
