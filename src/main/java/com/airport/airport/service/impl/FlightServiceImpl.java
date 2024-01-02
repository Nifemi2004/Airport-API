package com.airport.airport.service.impl;

import com.airport.airport.entity.Airline;
import com.airport.airport.entity.Airplane;
import com.airport.airport.entity.Flight;
import com.airport.airport.exception.AirportAPIException;
import com.airport.airport.exception.ResourceNotFoundException;
import com.airport.airport.payload.FlightDto;
import com.airport.airport.repository.AirlineRepository;
import com.airport.airport.repository.AirplaneRepository;
import com.airport.airport.repository.FlightRepository;
import com.airport.airport.service.FlightService;
import com.airport.airport.service.WeatherService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    private AirlineRepository airlineRepository;
    private AirplaneRepository airplaneRepository;
    private FlightRepository flightRepository;

    private WeatherService weatherService;

    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);

    public FlightServiceImpl(AirlineRepository airlineRepository, AirplaneRepository airplaneRepository, FlightRepository flightRepository, ModelMapper modelMapper, WeatherService weatherService) {
        this.airlineRepository = airlineRepository;
        this.airplaneRepository = airplaneRepository;
        this.flightRepository = flightRepository;
        this.modelMapper = modelMapper;
        this.weatherService = weatherService;
    }

    @Override
    public FlightDto createFlight(long airlineId, long airplaneId, FlightDto flightDto) {

        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new ResourceNotFoundException("Airline", "id", airlineId));

        Airplane airplane = airplaneRepository.findById(airplaneId).orElseThrow(
                () -> new ResourceNotFoundException("Airplane", "id", airplaneId));

        if(!(airplane.getAirline().getId() == (airline.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Airplane does not belong to the Airline");
        }

        Flight flight = mapToEntity(flightDto);

        flight.setAirplane(airplane);

        return mapToDTO(flightRepository.save(flight));
    }

    @Override
    public List<FlightDto> getFlightByAirplaneId(long airlineId, long airplaneId) {

        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new ResourceNotFoundException("Airline", "id", airlineId));

        Airplane airplane = airplaneRepository.findById(airplaneId).orElseThrow(
                () -> new ResourceNotFoundException("Airplane", "id", airplaneId));

        if(!(airplane.getAirline().getId() == (airline.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Airplane does not belong to the Airline");
        }

        List<Flight> flights = flightRepository.findByAirplaneId(airplane.getId());

        return flights.stream().map(flight -> mapToDTO(flight)).collect(Collectors.toList());
    }

    @Override
    public List<FlightDto> findAllFlightWithTheSameAirlines(long airlineId) {
        List<Flight> flights = flightRepository.findAllFlightWithTheSameAirlines(airlineId);

        return flights.stream().map(flight -> mapToDTO(flight)).collect(Collectors.toList());
    }

    @Override
    public List<FlightDto> findFlightsByAirlineAndConditions(Long airlineId, String origin, String destination, String departureDate, String arrivalDate) {
        List<Flight> flights = flightRepository.findFlightsByAirlineAndConditions(
                airlineId,
                origin,
                destination,
                departureDate,
                arrivalDate);

        return flights.stream().map(flight -> mapToDTO(flight)).collect(Collectors.toList());
    }





    @Override
    public FlightDto updateFlight(long airlineId, long airplaneId, long flightId, FlightDto flightDto) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new ResourceNotFoundException("Airline", "id", airlineId));

        Airplane airplane = airplaneRepository.findById(airplaneId).orElseThrow(
                () -> new ResourceNotFoundException("Airplane", "id", airplaneId));

        if(!(airplane.getAirline().getId() == (airline.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Airplane does not belong to the Airline");
        }

        Flight flight = flightRepository.findById(flightId).orElseThrow(
                () -> new ResourceNotFoundException("Flight", "id", flightId)
        );

        if(!(flight.getAirplane().getId() == (flight.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Flight does not belong to the Airplane");
        }

        Flight updatedFlight = mapToEntity(flightDto);

        flight.setFlightNumber(updatedFlight.getFlightNumber());
        flight.setDestination(updatedFlight.getDestination());
        flight.setOrigin(updatedFlight.getOrigin());
        flight.setArrivalDate(updatedFlight.getArrivalDate());
        flight.setDepartureDate(updatedFlight.getDepartureDate());

        flightRepository.save(flight);

        return mapToDTO(flight);
    }

    @Override
    public FlightDto getFlightById(long airlineId, long airplaneId, long flightId) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new ResourceNotFoundException("Airline", "id", airlineId));

        Airplane airplane = airplaneRepository.findById(airplaneId).orElseThrow(
                () -> new ResourceNotFoundException("Airplane", "id", airplaneId));

        if(!(airplane.getAirline().getId() == (airline.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Airplane does not belong to the Airline");
        }

        Flight flight = flightRepository.findById(flightId).orElseThrow(
                () -> new ResourceNotFoundException("Flight", "id", flightId)
        );


        if(!(flight.getAirplane().getId() == (airplane.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Flight does not belong to the Airplane");
        }

        if(weatherService.getWeatherByCity(flight.getDestination()).contains("Rain")){
            flight.setStatus("Delayed");
        }else {
            flight.setStatus("Active");
        }
        return mapToDTO(flight);
    }

    @Override
    public void deleteFlight(long airlineId, long airplaneId, long flightId) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new ResourceNotFoundException("Airline", "id", airlineId));

        Airplane airplane = airplaneRepository.findById(airplaneId).orElseThrow(
                () -> new ResourceNotFoundException("Airplane", "id", airplaneId));

        if(!(airplane.getAirline().getId() == (airline.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Airplane does not belong to the Airline");
        }

        Flight flight = flightRepository.findById(flightId).orElseThrow(
                () -> new ResourceNotFoundException("Flight", "id", flightId)
        );

        if(!(flight.getAirplane().getId() == (flight.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Flight does not belong to the Airplane");
        }

        flightRepository.delete(flight);
    }



    private Flight mapToEntity(FlightDto flightDto){
        Flight flight = modelMapper.map(flightDto, Flight.class);
        return flight;
    }

    //Convert entity to DTO
    private FlightDto mapToDTO(Flight flight){
        FlightDto flightDto = modelMapper.map(flight, FlightDto.class);
        return flightDto;
    }
}
