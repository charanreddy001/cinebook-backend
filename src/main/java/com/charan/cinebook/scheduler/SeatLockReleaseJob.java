package com.charan.cinebook.scheduler;

import com.charan.cinebook.models.SeatStatus;
import com.charan.cinebook.models.ShowSeat;
import com.charan.cinebook.repository.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SeatLockReleaseJob {

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Scheduled(fixedRate = 6000)
    public void releaseLocks() {
        LocalDateTime cutoff = LocalDateTime.now().minusSeconds(20);

        List<ShowSeat> expiredSeats = showSeatRepository.findByStatusAndLockedAtBefore(SeatStatus.LOCKED,cutoff);

        for(ShowSeat seat : expiredSeats) {
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setLockedBy(null);
            seat.setLockedBy(null);
            showSeatRepository.save(seat);
        }

        if(!expiredSeats.isEmpty()) {
            System.out.println("released " + expiredSeats.size() + " expired seat Locks ");
        }
    }
}
