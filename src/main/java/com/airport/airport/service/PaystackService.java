package com.airport.airport.service;

import com.airport.airport.payload.InitializePaymentDto;
import com.airport.airport.payload.InitializePaymentResponse;
import com.airport.airport.payload.PaymentVerificationResponse;
import org.springframework.stereotype.Service;

@Service
public interface PaystackService {
    InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto);
    PaymentVerificationResponse paymentVerification(String reference, Long id) throws Exception;
}
