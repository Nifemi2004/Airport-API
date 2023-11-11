package com.airport.airport.service;

import com.airport.airport.payload.AirplaneDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AirplaneService {
    AirplaneDto createAirplane(long airlineId, AirplaneDto airplaneDto);

    List<AirplaneDto> getAirplaneByAirlineId(long airlineId);

    AirplaneDto updateAirplane(long airlineId, long airplaneId, AirplaneDto airplaneDto);

    AirplaneDto getAirplaneById(long airlineId, long airplaneId);

    void deleteAirplane(long airlineId, long airplaneId);
}
