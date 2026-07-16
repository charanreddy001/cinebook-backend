package com.charan.cinebook.controller;

import com.charan.cinebook.service.ShowSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowSeatController {

    @Autowired
    private ShowSeatService showSeatService;

    @PostMapping("/{showId}/seats/lock")
    public String lockSeats(@PathVariable Long showId, @RequestBody List<Long> seatIds) {
        return showSeatService.lockSeats(showId, seatIds);
    }
}