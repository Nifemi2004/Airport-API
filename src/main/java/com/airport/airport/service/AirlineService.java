package com.airport.airport.service;

import com.airport.airport.payload.AirlineDto;

import java.util.List;

public interface AirlineService {
    AirlineDto createAirline(AirlineDto airlineDto);

    List<AirlineDto> getAllAirlines();

    AirlineDto getAirlineById(long id);

    AirlineDto updateAirline(AirlineDto updatedAirline, long id);

    void deleteAirline(long id);
}
