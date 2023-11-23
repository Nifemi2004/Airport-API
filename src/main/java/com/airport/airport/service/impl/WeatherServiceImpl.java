package com.airport.airport.service.impl;

import com.airport.airport.entity.Weather;
import com.airport.airport.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImpl implements WeatherService {


    @Value("${openweathermap.apikey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private final String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiKey}";

    @Autowired
    public WeatherServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public String getWeatherByCity(String city) {
        ResponseEntity<Weather> responseEntity = restTemplate.getForEntity(apiUrl, Weather.class, city, apiKey);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Weather weather = responseEntity.getBody();
            if (weather != null && weather.getWeather() != null && weather.getWeather().length > 0) {
                Weather.WeatherInfo weatherInfo = weather.getWeather()[0];
                return "Main: " + weatherInfo.getMain() + ", Description: " + weatherInfo.getDescription();
            }
        }

        return "Weather information not available";    }
}
