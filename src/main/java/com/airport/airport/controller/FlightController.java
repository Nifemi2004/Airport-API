package com.airport.airport.controller;

import com.airport.airport.payload.FlightDto;
import com.airport.airport.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airline/")
public class FlightController {

    private FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{airlineId}/airplane/{airplaneId}/flight")
    public ResponseEntity<FlightDto> createFlight(@PathVariable(name = "airplaneId") long airplaneId,
                                                  @PathVariable(name = "airlineId") long airlineId,
                                                  @RequestBody  FlightDto flightDto){
        return new ResponseEntity<>(flightService.createFlight(airplaneId, airlineId, flightDto), HttpStatus.CREATED);
    }

    @GetMapping("{airlineId}/airplane/{airplaneId}/flight")
    public List<FlightDto> getFlightByAirplaneId(@PathVariable(name = "airplaneId") long airplaneId,
                                                 @PathVariable(name = "airlineId") long airlineId
    ){
        return flightService.getFlightByAirplaneId(airplaneId, airlineId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{airlineId}/airplane/{airplaneId}/flight/{flightId}")
    public ResponseEntity<FlightDto> updateFlight(@PathVariable(name = "airplaneId") long airplaneId,
                                                  @PathVariable(name = "airlineId") long airlineId,
                                                  @PathVariable(name = "flightId") long flightId,
                                                  @RequestBody FlightDto flightDto){
        return new ResponseEntity<>(flightService.updateFlight(flightId, airplaneId, airlineId, flightDto), HttpStatus.OK);
    }

    @GetMapping("{airlineId}/airplane/{airplaneId}/flight/{flightId}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable(name = "airplaneId") long airplaneId,
                                                   @PathVariable(name = "airlineId") long airlineId,
                                                   @PathVariable(name = "flightId") long flightId){
        return new ResponseEntity<>(flightService.getFlightById(flightId, airplaneId, airlineId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{airlineId}/airplane/{airplaneId}/flight/{flightId}")
    public ResponseEntity<String> deleteFlight(@PathVariable(name = "airplaneId") long airplaneId,
                                               @PathVariable(name = "airlineId") long airlineId,
                                               @PathVariable(name = "flightId") long flightId){
        flightService.deleteFlight(airplaneId, flightId, airlineId);
        return  new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }
}
