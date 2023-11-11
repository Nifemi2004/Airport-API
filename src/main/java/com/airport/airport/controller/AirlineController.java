package com.airport.airport.controller;

import com.airport.airport.payload.AirlineDto;
import com.airport.airport.payload.AuthenticationResponse;
import com.airport.airport.service.AirlineService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airlines")
public class AirlineController {

    private AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    //Create new Airline
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AirlineDto> createAirline(@RequestBody AirlineDto airlineDto){
            return new ResponseEntity<>(airlineService.createAirline(airlineDto), HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<AirlineDto>> getAllAirlines(){
        return new ResponseEntity<>(airlineService.getAllAirlines(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineDto> getAirlineById(@PathVariable(name = "id") Long id){
         return new ResponseEntity<>(airlineService.getAirlineById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<AirlineDto> updateAirline(@PathVariable(name = "id") Long id, @RequestBody AirlineDto airlineDto){
        return new ResponseEntity<>(airlineService.updateAirline(airlineDto, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAirline(@PathVariable(name = "id") Long id){
        airlineService.deleteAirline(id);
        return new ResponseEntity<>("Deleted Successfully" ,HttpStatus.OK);
    }
}
