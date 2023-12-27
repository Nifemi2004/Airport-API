package com.airport.airport.service;

import com.airport.airport.entity.Weather;
import org.springframework.stereotype.Service;

@Service
public interface WeatherService {
    String getWeatherByCity(String city);
}
