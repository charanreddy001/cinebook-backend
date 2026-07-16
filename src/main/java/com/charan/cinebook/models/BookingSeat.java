package com.charan.cinebook.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "booking_seats")
@Data
public class BookingSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "show_seat_id")
    private ShowSeat showSeat;
}
