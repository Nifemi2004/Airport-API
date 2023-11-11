package com.airport.airport.payload;

import lombok.Data;

import java.util.Set;

@Data
public class AirlineDto {

    private Long id;
    private String name;
    private String headquarters;

    private Set<AirplaneDto> airplanes;

}
