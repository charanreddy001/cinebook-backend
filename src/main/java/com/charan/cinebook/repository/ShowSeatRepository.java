package com.charan.cinebook.repository;

import com.charan.cinebook.models.Seat;
import com.charan.cinebook.models.SeatStatus;
import com.charan.cinebook.models.Show;
import com.charan.cinebook.models.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {
        List<ShowSeat> findByShowId(Long showId);

        @Lock(LockModeType.PESSIMISTIC_WRITE)
        @Query("SELECT s FROM ShowSeat s WHERE s.id IN :ids")
        List<ShowSeat> findAllForUpdate(@Param("ids") List<Long> ids);

        List<ShowSeat> findByStatusAndLockedAtBefore(SeatStatus status, LocalDateTime cutoff);
}
