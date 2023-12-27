package com.airport.airport.payload;

import lombok.Data;

@Data
public class BaggageDto {
    private String baggageTag;
    private double weight;
    private String type;
}
