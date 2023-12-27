package com.airport.airport.service;

import com.airport.airport.payload.FlightDto;
import com.airport.airport.payload.FlightRouteDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightRouteService {
    FlightRouteDto createFlightRoute(Long airlineId, FlightRouteDto flightRouteDto);
    List<FlightRouteDto> getAllFlightRoute(Long airlineId);
    List<String> getDestinationFromOrigin(Long airlineId, Long id, String origin);
    FlightRouteDto updateFlightRoute(Long airlineId, Long id, FlightRouteDto flightRouteDto);
    void deleteFlightRoute(Long airlineId, Long id);
}
