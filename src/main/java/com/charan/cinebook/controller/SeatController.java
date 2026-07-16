package com.charan.cinebook.controller;

import com.charan.cinebook.models.Seat;
import com.charan.cinebook.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatRepository seatRepository;

    @PostMapping
    public Seat create(@RequestBody Seat seat) {
        return seatRepository.save(seat);
    }

    @GetMapping
    public List<Seat> getAll() {
        return seatRepository.findAll();
    }

    @GetMapping("/{id}")
    public Seat getById(@PathVariable Long id) {
        return seatRepository.findById(id).orElseThrow(() -> new RuntimeException("Seat not Found"));
    }

    @PutMapping("/{id}")
    public Seat update(@PathVariable Long id, @RequestBody Seat updated) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        seat.setSeatNumber(updated.getSeatNumber());
        seat.setSeatRow(updated.getSeatRow());
        seat.setType(updated.getType());
        seat.setScreen(updated.getScreen());
        return seatRepository.save(seat);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        seatRepository.deleteById(id);
    }
}
