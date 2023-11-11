package com.airport.airport.controller;

import com.airport.airport.payload.AirplaneDto;
import com.airport.airport.service.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class AirplaneController {

    private AirplaneService airplaneService;


    public AirplaneController(AirplaneService airplaneService) {
        this.airplaneService = airplaneService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("airlines/{airlineId}/airplane")
    public ResponseEntity<AirplaneDto> createNewAirplane(@PathVariable(name = "airlineId") long airlineId, @RequestBody AirplaneDto airplaneDto){

        return new ResponseEntity<>(airplaneService.createAirplane(airlineId, airplaneDto), HttpStatus.CREATED);
    }

    @GetMapping("airlines/{airlineId}/airplane")
    public List<AirplaneDto> getAllAirplanes(@PathVariable(name = "airlineId") long airlineId){
        return airplaneService.getAirplaneByAirlineId(airlineId);
    }

    @GetMapping("airlines/{airlineId}/airplane/{airplaneId}")
    public ResponseEntity<AirplaneDto> getAirplaneById(@PathVariable(name = "airlineId") long airlineId,
                                       @PathVariable(name = "airplaneId") long airplaneId){
        return new ResponseEntity<>(airplaneService.getAirplaneById(airlineId, airplaneId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("airlines/{airlineId}/airplane/{airplaneId}")
    public ResponseEntity<AirplaneDto> updateAirplane(@PathVariable(name = "airlineId") long airlineId,
                                     @PathVariable(name = "airplaneId") long airplaneId,
                                     @RequestBody AirplaneDto airplaneDto){
        return new ResponseEntity<>(airplaneService.updateAirplane(airplaneId, airlineId, airplaneDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("airlines/{airlineId}/airplane/{airplaneId}")
    public ResponseEntity<String> deleteAirplane(@PathVariable(name = "airlineId") long airlineId,
                                                 @PathVariable(name = "airplaneId") long airplaneId){
        airplaneService.deleteAirplane(airlineId, airplaneId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

}
