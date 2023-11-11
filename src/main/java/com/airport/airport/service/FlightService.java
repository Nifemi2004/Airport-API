package com.airport.airport.service;

import com.airport.airport.payload.FlightDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightService {

    FlightDto createFlight(long airlineId, long airplaneId, FlightDto flightDto);

    List<FlightDto> getFlightByAirplaneId(long airlineId, long airplaneId);

    FlightDto updateFlight(long airlineId, long airplaneId, long flightId, FlightDto flightDto);

    FlightDto getFlightById(long airlineId, long airplaneId, long flightId);

    void deleteFlight(long airlineId, long airplaneId, long flightId);
}
