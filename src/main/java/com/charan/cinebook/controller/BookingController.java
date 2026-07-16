package com.charan.cinebook.controller;

import com.charan.cinebook.models.Booking;
import com.charan.cinebook.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/confirm")
    public Booking confirmBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.confirmBooking(bookingRequest);
    }
}
