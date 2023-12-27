package com.airport.airport.payload;

import com.airport.airport.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class PaymentVerificationDto {

        @JsonProperty("member_id")
        private User user;

        @JsonProperty("reference")
        private String reference;

        @JsonProperty("amount")
        private int amount;

        @JsonProperty("gateway_response")
        private String gatewayResponse;

        @JsonProperty("paid_at")
        private String paidAt;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("channel")
        private String channel;

        @JsonProperty("currency")
        private String currency;

        @JsonProperty("ip_address")
        private String ipAddress;

        @JsonProperty("created_on")
        private Date createdOn = new Date();
    }

