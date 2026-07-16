package com.charan.cinebook.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "seats")
@Data
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String seatNumber;
    private String seatRow;

    @Enumerated(EnumType.STRING)
    private SeatType type;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;
}
