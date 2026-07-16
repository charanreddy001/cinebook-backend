package com.charan.cinebook.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "shows")
@Data
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // many shows in one screen
    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

    // many shows for one movie in different
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal price;
}
