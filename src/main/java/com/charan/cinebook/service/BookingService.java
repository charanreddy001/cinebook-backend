package com.charan.cinebook.service;

import com.charan.cinebook.controller.BookingRequest;
import com.charan.cinebook.models.*;
import com.charan.cinebook.repository.BookingRepository;
import com.charan.cinebook.repository.BookingSeatRepository;
import com.charan.cinebook.repository.ShowSeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private BookingSeatRepository bookingSeatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentService paymentService;

    // this annotation means do complete or do nothing
    @Transactional
    public Booking confirmBooking(BookingRequest bookingRequest) {

        var existing  = bookingRepository.findByIdempotencyKey(bookingRequest.getIdempotencyKey());
        if(existing.isPresent()) {
            return existing.get();
        }

        List<ShowSeat> seats = showSeatRepository.findAllById(bookingRequest.getShowSeatIds());

        for(ShowSeat seat : seats) {
            if(seat.getStatus() != SeatStatus.LOCKED) {
                throw new RuntimeException("this seat is not locked" + seat.getId());
            }
        }

        Booking booking = new Booking();
        booking.setUser(bookingRequest.getUser());
        booking.setShow(seats.get(0).getShow());
        booking.setTotal_amount(bookingRequest.getTotalAmount());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setIdempotencyKey(bookingRequest.getIdempotencyKey());
        booking.setStatus(BookingStatus.PENDING);
        Booking savedBooking = bookingRepository.save(booking);

        Payment payment = paymentService.processPayment(savedBooking);

        if(payment.getStatus() == PaymentStatus.SUCCESS) {
            savedBooking.setStatus(BookingStatus.CONFIRMED);

            for(ShowSeat seat : seats) {
                seat.setStatus(SeatStatus.BOOKED);
                showSeatRepository.save(seat);

                BookingSeat bookingSeat = new BookingSeat();
                bookingSeat.setBooking(savedBooking);
                bookingSeat.setShowSeat(seat);
                bookingSeatRepository.save(bookingSeat);
            }
        }else {
            savedBooking.setStatus(BookingStatus.CANCELLED);
        }
        return bookingRepository.save(savedBooking);
    }
}
