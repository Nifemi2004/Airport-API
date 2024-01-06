package com.airport.airport.service;

import com.airport.airport.payload.FlightDto;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public interface FlightService {

    FlightDto createFlight(long airlineId, long airplaneId, FlightDto flightDto);

    List<FlightDto> getFlightByAirplaneId(long airlineId, long airplaneId);

    List<FlightDto> findAllFlightWithTheSameAirlines(long airlineId);

    List<FlightDto> findFlightsByAirlineAndConditions(Long airlineId,
                                                      String origin,
                                                      String destination,
                                                      String departureDate,
                                                      String arrivalDate);

    List<FlightDto> findFlightsWithLowestPricePerDay(long airlineId, String origin, String destination);

    FlightDto updateFlight(long airlineId, long airplaneId, long flightId, FlightDto flightDto);

    FlightDto getFlightById(long airlineId, long airplaneId, long flightId);

    void deleteFlight(long airlineId, long airplaneId, long flightId);
}
