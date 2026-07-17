package com.charan.cinebook.controller;

import com.charan.cinebook.models.*;
import com.charan.cinebook.repository.*;
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

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @PostMapping
    public Show create(@RequestBody Show show) {
        Show savedShow = showRepository.save(show);
        Movie movie = movieRepository.findById(show.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("movie Not Found"));

        Screen screen = screenRepository.findById(show.getScreen().getId())
                .orElseThrow(() -> new RuntimeException("screen not found"));

        show.setMovie(movie);
        show.setScreen(screen);

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

        Movie movie = movieRepository.findById(updated.getMovie().getId())
                        .orElseThrow(() -> new RuntimeException("Movie Not Found"));

        Screen screen = screenRepository.findById(updated.getScreen().getId())
                        .orElseThrow(() -> new RuntimeException("Screen Not Found"));

        updated.setScreen(screen);
        updated.setMovie(movie);

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
