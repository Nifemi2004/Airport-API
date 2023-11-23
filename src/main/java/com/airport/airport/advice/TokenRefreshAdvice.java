package com.airport.airport.advice;

import com.airport.airport.exception.TokenRefreshException;
import com.airport.airport.payload.ErrorDetails;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class TokenRefreshAdvice {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDetails handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return new ErrorDetails(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        }
}
