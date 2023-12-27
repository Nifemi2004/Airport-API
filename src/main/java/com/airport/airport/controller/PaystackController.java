package com.airport.airport.controller;

import com.airport.airport.payload.InitializePaymentDto;
import com.airport.airport.payload.InitializePaymentResponse;
import com.airport.airport.payload.PaymentVerificationResponse;
import com.airport.airport.service.PaystackService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/paystack",
        produces = MediaType. APPLICATION_JSON_VALUE
)
public class PaystackController {

    private final PaystackService paystackService;

    public PaystackController(PaystackService paystackService) {
        this.paystackService = paystackService;
    }

    @PostMapping("/initializepayment")
    public InitializePaymentResponse initializePayment(@Validated @RequestBody InitializePaymentDto initializePaymentDto) throws Throwable {
        return paystackService.initializePayment(initializePaymentDto);
    }

    @GetMapping("/verifypayment/{reference}/{id}")
    public PaymentVerificationResponse paymentVerification(@PathVariable(value = "reference") String reference,
                                                           @PathVariable(value = "id") Long id) throws Exception {
        if (reference.isEmpty()) {
            throw new Exception("reference and id must be provided in path");
        }
        return paystackService.paymentVerification(reference, id);
    }
}