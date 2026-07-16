package com.charan.cinebook.service;

import com.charan.cinebook.models.SeatStatus;
import com.charan.cinebook.models.ShowSeat;
import com.charan.cinebook.repository.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShowSeatService {

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Transactional
    public String lockSeats(Long showId, List<Long> seatIds) {

        List<ShowSeat> seats = showSeatRepository.findAllForUpdate(seatIds);

        for (ShowSeat seat : seats) {
            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                throw new RuntimeException("Seat " + seat.getId() + " is not available");
            }
        }

        for (ShowSeat seat : seats) {
            seat.setStatus(SeatStatus.LOCKED);
            seat.setLockedBy(1L); // hardcoded for now
            seat.setLockedAt(LocalDateTime.now());
            showSeatRepository.save(seat);
        }

        return "Seats locked successfully";
    }
}