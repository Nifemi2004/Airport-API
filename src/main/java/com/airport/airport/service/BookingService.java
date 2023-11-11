package com.airport.airport.service;

import com.airport.airport.payload.BookingDto;
import com.airport.airport.payload.BookingResponse;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {
    BookingResponse createBooking(long airlineId, long airplaneId, long flightId, BookingDto bookingDto);
}
