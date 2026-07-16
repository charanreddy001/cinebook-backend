package com.charan.cinebook.service;

import com.charan.cinebook.controller.BookingRequest;
import com.charan.cinebook.models.Booking;
import com.charan.cinebook.models.SeatStatus;
import com.charan.cinebook.models.ShowSeat;
import com.charan.cinebook.repository.BookingRepository;
import com.charan.cinebook.repository.BookingSeatRepository;
import com.charan.cinebook.repository.ShowSeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private PaymentService paymentService;

    @Mock
    private ShowSeatRepository showSeatRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingSeatRepository bookingSeatRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void shouldThrowExceptionWhenSeatIsNotLocked() {
        ShowSeat showSeat = new ShowSeat();
        showSeat.setStatus(SeatStatus.AVAILABLE);
        showSeat.setId(1L);

        when(bookingRepository.findByIdempotencyKey(anyString())).thenReturn(Optional.empty());
        when(showSeatRepository.findAllById(anyList())).thenReturn(List.of(showSeat));

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setShowSeatIds(List.of(1L));
        bookingRequest.setIdempotencyKey("test-key");
        bookingRequest.setTotalAmount(BigDecimal.valueOf(250));

        assertThrows(RuntimeException.class, ()-> bookingService.confirmBooking(bookingRequest));
    }

    @Test
    void ShouldReturnExistingBookingForDuplicateIdempotencyKey() {
        Booking existingBooking = new Booking();
        existingBooking.setId(99L);
        existingBooking.setIdempotencyKey("duplicate-key");

        when(bookingRepository.findByIdempotencyKey("duplicate-key"))
                .thenReturn(Optional.of(existingBooking));

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setIdempotencyKey("duplicate-key");

        Booking result = bookingService.confirmBooking(bookingRequest);

        assertEquals(99L,result.getId());

        verify(bookingRepository, never()).save(any());
    }
}
