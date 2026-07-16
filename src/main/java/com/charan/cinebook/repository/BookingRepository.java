package com.charan.cinebook.repository;

import com.charan.cinebook.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    Optional<Booking> findByIdempotencyKey(String idempotencyKey);
}
