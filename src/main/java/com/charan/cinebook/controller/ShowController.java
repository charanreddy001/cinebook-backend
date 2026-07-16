package com.charan.cinebook.controller;

import com.charan.cinebook.models.Seat;
import com.charan.cinebook.models.SeatStatus;
import com.charan.cinebook.models.Show;
import com.charan.cinebook.models.ShowSeat;
import com.charan.cinebook.repository.SeatRepository;
import com.charan.cinebook.repository.ShowRepository;
import com.charan.cinebook.repository.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private SeatRepository seatRepository;

    @PostMapping
    public Show create(@RequestBody Show show) {
        Show savedShow = showRepository.save(show);
        // when we create a new show then we can say all the seats are empty initially
        List<Seat> seats = seatRepository.findByScreenId(savedShow.getScreen().getId());
        for(Seat seat : seats) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(savedShow);
            showSeat.setSeat(seat);
            showSeat.setStatus(SeatStatus.AVAILABLE);
            showSeatRepository.save(showSeat);
        }
        return savedShow;
    }

    @GetMapping
    public List<Show> getAll() {
        return showRepository.findAll();
    }

    @GetMapping("/{id}")
    public Show getById(@PathVariable Long id) {
        return showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found"));
    }

    @PutMapping("/{id}")
    public Show update(@PathVariable Long id, @RequestBody Show updated) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found"));
        show.setMovie(updated.getMovie());
        show.setScreen(updated.getScreen());
        show.setStartTime(updated.getStartTime());
        show.setEndTime(updated.getEndTime());
        show.setPrice(updated.getPrice());
        return showRepository.save(show);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        showRepository.deleteById(id);
    }

    @GetMapping("/{show_id}/seats")
    public List<ShowSeat> getSeatsForShow(@PathVariable long show_id) {
        return showSeatRepository.findByShowId(show_id);
    }
}
