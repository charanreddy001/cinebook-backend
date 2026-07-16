package com.charan.cinebook.controller;

import com.charan.cinebook.models.Movie;
import com.charan.cinebook.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @PostMapping
    public Movie create(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @GetMapping
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public Movie getById(@PathVariable Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    @PutMapping("/{id}")
    public Movie update(@PathVariable Long id, @RequestBody Movie updated) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setTitle(updated.getTitle());
        movie.setDurationMinutes(updated.getDurationMinutes());
        movie.setGenre(updated.getGenre());
        movie.setLanguage(updated.getLanguage());
        return movieRepository.save(movie);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        movieRepository.deleteById(id);
    }
}