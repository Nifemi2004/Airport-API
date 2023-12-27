package com.airport.airport.service.impl;

import com.airport.airport.entity.Airline;
import com.airport.airport.entity.Airplane;
import com.airport.airport.entity.FlightRoute;
import com.airport.airport.exception.AirportAPIException;
import com.airport.airport.exception.ResourceNotFoundException;
import com.airport.airport.payload.FlightDto;
import com.airport.airport.payload.FlightRouteDto;
import com.airport.airport.repository.AirlineRepository;
import com.airport.airport.repository.FlightRouteRepository;
import com.airport.airport.service.FlightRouteService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightRouteServiceImpl implements FlightRouteService {

    private ModelMapper modelMapper;
    private AirlineRepository airlineRepository;
    private FlightRouteRepository flightRouteRepository;

    public FlightRouteServiceImpl(ModelMapper modelMapper,
                                  FlightRouteRepository flightRouteRepository,
                                  AirlineRepository airlineRepository) {
        this.modelMapper = modelMapper;
        this.airlineRepository = airlineRepository;
        this.flightRouteRepository = flightRouteRepository;
    }
    @Override
    public FlightRouteDto createFlightRoute(Long airlineId, FlightRouteDto flightRouteDto) {
        FlightRoute flightRoute = mapToEntity(flightRouteDto);

        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new ResourceNotFoundException("Airline", "id", airlineId));

        flightRoute.setAirline(airline);

        return mapToDTO(flightRouteRepository.save(flightRoute));
    }

    @Override
    public List<FlightRouteDto> getAllFlightRoute(Long airlineId) {
        List<FlightRoute> flightRoutes = flightRouteRepository.findByAirlineId(airlineId);

        return flightRoutes.stream().map(flightRoute -> mapToDTO(flightRoute)).collect(Collectors.toList());
    }

    public List<String> getDestinationFromOrigin(Long airlineId, Long id, String origin) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new ResourceNotFoundException("Airline", "id", airlineId));

        FlightRoute flightRoute = flightRouteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Flight route", "id", id));

        if(!(flightRoute.getAirline().getId() == (airline.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Flight Route does not belong to the Airline");
        }

        List<String> routes = flightRouteRepository.findDestinationByOrigin(origin);
        return routes;
    }

    @Override
    public FlightRouteDto updateFlightRoute(Long airlineId, Long id, FlightRouteDto flightRouteDto) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new ResourceNotFoundException("Airline", "id", airlineId));

        FlightRoute flightRoute = flightRouteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Flight route", "id", id));

        if(!(flightRoute.getAirline().getId() == (airline.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Flight Route does not belong to the Airline");
        }

        flightRoute.setDestination(flightRouteDto.getDestination());
        flightRoute.setOrigin(flightRouteDto.getOrigin());

        FlightRoute updatedFlightRoute = flightRouteRepository.save(flightRoute);
        return mapToDTO(updatedFlightRoute);    }

    @Override
    public void deleteFlightRoute(Long airlineId, Long id) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new ResourceNotFoundException("Airline", "id", airlineId));

        FlightRoute flightRoute = flightRouteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Flight route", "id", id));

        if(!(flightRoute.getAirline().getId() == (airline.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Flight Route does not belong to the Airline");
        }

        flightRouteRepository.delete(flightRoute);
    }

    private FlightRoute mapToEntity(FlightRouteDto flightRouteDto){
        FlightRoute flightRoute = modelMapper.map(flightRouteDto, FlightRoute.class);
        return flightRoute;
    }

    //Convert entity to DTO
    private FlightRouteDto mapToDTO(FlightRoute flightRoute){
        FlightRouteDto flightRouteDto = modelMapper.map(flightRoute, FlightRouteDto.class);
        return flightRouteDto;
    }
}
