package com.charan.cinebook.service;

import com.charan.cinebook.models.Booking;
import com.charan.cinebook.models.Payment;
import com.charan.cinebook.models.PaymentStatus;
import com.charan.cinebook.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment processPayment(Booking booking) {
        Payment payment = new Payment();

        payment.setAmount(booking.getTotalAmount());
        payment.setBooking(booking);
        payment.setTransactionId(UUID.randomUUID().toString());

        boolean success = Math.random() > 0.1;
        payment.setStatus(success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);

        return paymentRepository.save(payment);
    }
}
