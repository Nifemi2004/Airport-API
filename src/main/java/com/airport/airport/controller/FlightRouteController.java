package com.airport.airport.controller;

import com.airport.airport.payload.AirplaneDto;
import com.airport.airport.payload.FlightRouteDto;
import com.airport.airport.service.FlightRouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/")
public class FlightRouteController {

    private FlightRouteService flightRouteService;

    public FlightRouteController(FlightRouteService flightRouteService) {
        this.flightRouteService = flightRouteService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("airlines/{airlineId}/flightRoute")
    public ResponseEntity<FlightRouteDto> createNewFlightRoute(@PathVariable(name = "airlineId") long airlineId,
                                                               @RequestBody FlightRouteDto flightRouteDto){

        return new ResponseEntity<>(flightRouteService.createFlightRoute(airlineId, flightRouteDto), HttpStatus.CREATED);
    }

    @GetMapping("airlines/{airlineId}/flightRoute/{id}")
    public ResponseEntity<List<String>> getDestinationByOrigin(@PathVariable(name = "airlineId") long airlineId,
                                                               @PathVariable(name = "id") long id,
                                                               @RequestParam String origin){
        return new ResponseEntity<>(flightRouteService.getDestinationFromOrigin(airlineId, id,origin), HttpStatus.OK);
    }

    @GetMapping("airlines/{airlineId}/flightRoute")
    public List<FlightRouteDto> getAllFlightRouteByAirlineId(@PathVariable(name = "airlineId") long airlineId){
        return flightRouteService.getAllFlightRoute(airlineId);
    }
}
