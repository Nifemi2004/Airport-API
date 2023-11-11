package com.airport.airport.service.impl;

import com.airport.airport.entity.Airline;
import com.airport.airport.entity.Airplane;
import com.airport.airport.entity.Booking;
import com.airport.airport.entity.Flight;
import com.airport.airport.exception.AirportAPIException;
import com.airport.airport.exception.ResourceNotFoundException;
import com.airport.airport.payload.AirplaneDto;
import com.airport.airport.payload.BookingDto;
import com.airport.airport.payload.BookingResponse;
import com.airport.airport.repository.*;
import com.airport.airport.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private String lastSeatNumber;
    private AirlineRepository airlineRepository;
    private AirplaneRepository airplaneRepository;
    private FlightRepository flightRepository;
    private  BookingRepository bookingRepository;
    private ModelMapper modelMapper;

    public BookingServiceImpl( AirlineRepository airlineRepository, AirplaneRepository airplaneRepository, FlightRepository flightRepository, BookingRepository bookingRepository, ModelMapper modelMapper) {
        this.airlineRepository = airlineRepository;
        this.airplaneRepository = airplaneRepository;
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BookingResponse createBooking(long airlineId, long airplaneId, long flightId, BookingDto bookingDto) {
        Airline airline = airlineRepository.findById(airlineId).orElseThrow(
                () -> new ResourceNotFoundException("Airline", "id", airlineId));

        Airplane airplane = airplaneRepository.findById(airplaneId).orElseThrow(
                () -> new ResourceNotFoundException("Airplane", "id", airplaneId));

        if(!(airplane.getAirline().getId() == (airline.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Airplane does not belong to the Airline");
        }

        Flight flight = flightRepository.findById(flightId).orElseThrow(
                () -> new ResourceNotFoundException("Flight", "id", flightId)
        );

        if(!(flight.getAirplane().getId() == (flight.getId()))) {
            throw new AirportAPIException(HttpStatus.BAD_REQUEST, "Flight does not belong to the Airplane");
        }

        String seatNumber = assignSeat(flightId, airplaneId);

        Booking booking = mapToEntity(bookingDto);
        booking.setFlight(flight);
        booking.setSeatNumber(seatNumber);

        bookingRepository.save(booking);

        return BookingResponse.builder()
                .passengerFullName(bookingDto.getPassengerFullName())
                .seatNumber(seatNumber)
                .build();
    }

    public String assignSeat(long airplaneId, long flightId) {
        long totalSeats = airplaneRepository.getTotalSeatsByAirplaneId(airplaneId);

        String[] rowLetters = {"A", "B", "C", "D"};

        long seatsPerRow = totalSeats / rowLetters.length;

        String lastAssignedSeat = getLastAssignedSeatByFlightId(flightId);

        if(lastAssignedSeat == null){
           String nextSeat = "A1";
           return nextSeat;
        }else {

            int[] numericSeat = convertFromCustomSeatNumber(lastAssignedSeat);
            int row = numericSeat[0];
            int column = numericSeat[1];

            column++;

            if (column > seatsPerRow) {
                row++;
                column = 1;
            }

            if (row >= rowLetters.length) {
                return "No available seats.";
            }

            String nextSeat = convertToCustomSeatNumber(row, column, rowLetters[row]);

            lastSeatNumber = nextSeat;

            return nextSeat;
        }

    }

    public String getLastAssignedSeatByFlightId(Long flightId) {
        if(lastSeatNumber == null) {
            List<String> seatNumbers = bookingRepository.findLastAssignedSeatByFlightId(flightId);

            if (seatNumbers.isEmpty()) {
                return null;
            } else {
                String lastAssignedSeat = seatNumbers.get(0);
                lastSeatNumber = lastAssignedSeat;
                return lastAssignedSeat;
            }
        }else {
            return lastSeatNumber;
        }
    }

    public int[] convertFromCustomSeatNumber(String seatNumber) {
        char rowLetter = seatNumber.charAt(0);
        int column = Integer.parseInt(seatNumber.substring(1));
        int row = rowLetter - 'A';
        return new int[] {row, column};
    }

    public String convertToCustomSeatNumber(int row, int column, String rowLetter) {
        return rowLetter + column;
    }


    private Booking mapToEntity(BookingDto bookingDto){
        Booking booking = modelMapper.map(bookingDto, Booking.class);
        return booking;
    }


    private BookingDto mapToDTO(Booking booking){
        BookingDto bookingDto = modelMapper.map(booking, BookingDto.class);
        return bookingDto;
    }
}
