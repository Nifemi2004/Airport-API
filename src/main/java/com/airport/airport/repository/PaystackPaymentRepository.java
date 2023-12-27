package com.airport.airport.repository;

import com.airport.airport.entity.PaymentPaystack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaystackPaymentRepository extends JpaRepository<PaymentPaystack, Long> {
}
