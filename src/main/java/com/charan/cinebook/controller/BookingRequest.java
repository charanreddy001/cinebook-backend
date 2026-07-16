package com.charan.cinebook.controller;

import com.charan.cinebook.models.ShowSeat;
import com.charan.cinebook.models.User;

import java.math.BigDecimal;
import java.util.List;

public class BookingRequest {
    private List<Long> showSeatIds;
    private User user;
    private BigDecimal totalAmount;
    private String idempotencyKey;

    public List<Long> getShowSeatIds() {
        return showSeatIds;
    }

    public void setShowSeatIds(List<Long> showSeatIds) {
        this.showSeatIds = showSeatIds;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }
}
