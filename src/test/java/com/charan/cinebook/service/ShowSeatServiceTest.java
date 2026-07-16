package com.charan.cinebook.service;


import com.charan.cinebook.models.SeatStatus;
import com.charan.cinebook.models.ShowSeat;
import com.charan.cinebook.repository.ShowSeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShowSeatServiceTest {

    @Mock
    private ShowSeatRepository showSeatRepository;

    @InjectMocks
    private ShowSeatService showSeatService;

    @Test
    void ShouldLockAvailableSeatsSuccessfully() {
        ShowSeat seat = new ShowSeat();
        seat.setId(1L);
        seat.setStatus(SeatStatus.AVAILABLE);

        when(showSeatRepository.findAllForUpdate(anyList()))
                .thenReturn(List.of(seat));

        String result = showSeatService.lockSeats(1L,List.of(1L));

        assertEquals("Seats locked successfully",result);
        assertEquals(SeatStatus.LOCKED,seat.getStatus());
        verify(showSeatRepository,times(1)).save(seat);
    }

    @Test
    void shouldThrowExceptionWhenSeatAlreadyLocked() {
        ShowSeat seat = new ShowSeat();
        seat.setId(1L);
        seat.setStatus(SeatStatus.LOCKED);

        when(showSeatRepository.findAllForUpdate(anyList()))
                .thenReturn(List.of(seat));

        assertThrows(RuntimeException.class, () -> showSeatService.lockSeats(1L,List.of(1L)));
    }
}
