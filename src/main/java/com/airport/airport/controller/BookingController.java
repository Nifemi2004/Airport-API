package com.airport.airport.controller;

import com.airport.airport.payload.BookingDto;
import com.airport.airport.payload.BookingResponse;
import com.airport.airport.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/airline/")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("{airlineId}/airplane/{airplaneId}/flight/{flightId}/booking")
    public ResponseEntity<BookingResponse> createBooking(@PathVariable(name = "airlineId") long airlineId,
                                                         @PathVariable(name = "airplaneId") long airplaneId,
                                                         @PathVariable(name = "flightId") long flightId,
                                                         @RequestBody BookingDto bookingDto){
        return new ResponseEntity<>(bookingService.createBooking(airlineId, airplaneId, flightId, bookingDto), HttpStatus.OK);
    }
}
