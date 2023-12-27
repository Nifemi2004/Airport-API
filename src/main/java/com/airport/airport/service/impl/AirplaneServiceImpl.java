package com.airport.airport.service.impl;

import com.airport.airport.entity.Airline;
import com.airport.airport.entity.Airplane;
import com.airport.airport.exception.AirportAPIException;
import com.airport.airport.exception.ResourceNotFoundException;
import com.airport.airport.payload.AirplaneDto;
import com.airport.airport.repository.AirlineRepository;
import com.airport.airport.repository.AirplaneRepository;
import com.airport.airport.service.AirplaneService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AirplaneServiceImpl implements AirplaneService {

    private AirplaneRepository airplaneRepository;

    private AirlineRepository airlineRepository;
    private ModelMapper modelMapper;

    public AirplaneServiceImpl(AirplaneRepository airplaneRepository,
                               ModelMapper modelMapper,
                               AirlineRepository airlineRepository) {
        this.airplaneRepository = airplaneRepository;
        this.modelMapper = modelMapper;
        this.airlineRepository = airlineRepository;
    }

    @Override
    public AirplaneDto createAirplane(long airlineId, AirplaneDto airplaneDto) {
        Airplane airplane = mapToEntity(airplaneDto);

        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new ResourceNotFoundException("Airline", "id", airlineId));

        airplane.setAirline(airline);

        return mapToDTO(airplaneRepository.save(airplane));
    }

    @Override
    public List<AirplaneDto> getAirplaneByAirlineId(long airlineId) {
        List<Airplane> airplanes = airplaneRepository.findByAirlineId(airlineId);

        return airplanes.stream().map(airplane -> mapToDTO(airplane)).collect(Collectors.toList());
    }

    @Override
    public List<AirplaneDto> getAllAirplanes() {
        try {
            List<Airplane> airplanes = airplaneRepository.findAllAirplanesWithAirlines();
            return airplanes.stream().map(airplane -> mapToDTO(airplane)).collect(Collectors.toList());
        }catch (Exception e){
            log.error("Error in database operation: {}", e.getMessage(), e);
            // Rethrow or handle the exception as needed
            throw new RuntimeException("Error in database operation", e);
        }

    }

    @Override
    public AirplaneDto updateAirplane(long airlineId, long airplaneId, AirplaneDto airplaneDto) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new ResourceNotFoundException("Airline", "id", airlineId));

        Airplane airplane = airplaneRepository.findById(airplaneId).orElseThrow(
                () -> new ResourceNotFoundException("Airplane", "id", airplaneId));

        if(!(airplane.getAirline().getId() == (airline.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Airplane does not belong to the Airline");
        }

        airplane.setAircraftRegistration(airplaneDto.getAircraftRegistration());
        airplane.setCode(airplaneDto.getCode());
        airplane.setModel(airplaneDto.getModel());
        airplane.setCapacity(airplaneDto.getCapacity());

        Airplane updatedPlane = airplaneRepository.save(airplane);
        return mapToDTO(updatedPlane);
    }

    @Override
    public AirplaneDto getAirplaneById(long airlineId, long airplaneId) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new ResourceNotFoundException("Airline", "id", airlineId));

        Airplane airplane = airplaneRepository.findById(airplaneId).orElseThrow(
                () -> new ResourceNotFoundException("Airplane", "id", airplaneId));

        if(!(airplane.getAirline().getId() == (airline.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Airplane does not belong to the Airline");
        }

        return mapToDTO(airplane);
    }

    @Override
    public void deleteAirplane(long airlineId, long airplaneId) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new ResourceNotFoundException("Airline", "id", airlineId));

        Airplane airplane = airplaneRepository.findById(airplaneId).orElseThrow(
                () -> new ResourceNotFoundException("Airplane", "id", airplaneId));

        if(!(airplane.getAirline().getId() == (airline.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Airplane does not belong to the Airline");
        }

        airplaneRepository.delete(airplane);
    }


    private Airplane mapToEntity(AirplaneDto airplaneDto){
        Airplane airplane = modelMapper.map(airplaneDto, Airplane.class);
        return airplane;
    }

    //Convert entity to DTO
    private AirplaneDto mapToDTO(Airplane airplane){
        AirplaneDto airplaneDto = modelMapper.map(airplane, AirplaneDto.class);
        return airplaneDto;
    }


}
