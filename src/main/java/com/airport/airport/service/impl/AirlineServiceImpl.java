package com.airport.airport.service.impl;

import com.airport.airport.entity.Airline;
import com.airport.airport.exception.ResourceNotFoundException;
import com.airport.airport.payload.AirlineDto;
import com.airport.airport.repository.AirlineRepository;
import com.airport.airport.service.AirlineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirlineServiceImpl implements AirlineService {

    private AirlineRepository airlineRepository;

    private ModelMapper modelMapper;

    public AirlineServiceImpl(AirlineRepository airlineRepository, ModelMapper modelMapper) {
        this.airlineRepository = airlineRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public AirlineDto createAirline(AirlineDto airlineDto) {
        Airline airline = airlineRepository.save(mapToEntity(airlineDto));

        AirlineDto airlineResponse = mapToDTO(airline);

        return airlineResponse;
    }

    @Override
    public List<AirlineDto> getAllAirlines() {
        List<Airline> listOfAirline = airlineRepository.findAll();

        List<AirlineDto> AirlineResponse = listOfAirline.stream().map(airline -> mapToDTO(airline)).collect(Collectors.toList());

        return AirlineResponse;
    }

    @Override
    public AirlineDto getAirlineById(long id) {
        Airline airlineById = airlineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Airline", "id", id));
        return mapToDTO(airlineById);
    }

    @Override
    public AirlineDto updateAirline(AirlineDto updatedAirline, long id) {
        Airline airlineById = airlineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Airline", "id", id));

        Airline update = mapToEntity(updatedAirline);

        airlineById.setName(update.getName());
        airlineById.setHeadquarters(update.getHeadquarters());

        airlineRepository.save(airlineById);

        return mapToDTO(airlineById);
    }

    @Override
    public void deleteAirline(long id) {
        airlineRepository.deleteById(id);
    }


    private Airline mapToEntity(AirlineDto airlineDto){
        Airline airline = modelMapper.map(airlineDto, Airline.class);
        return airline;
    }

    //Convert entity to DTO
    private AirlineDto mapToDTO(Airline airline){
        AirlineDto airlineDto = modelMapper.map(airline, AirlineDto.class);
        return airlineDto;
    }
}
