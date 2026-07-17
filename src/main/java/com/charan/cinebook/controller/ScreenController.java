package com.charan.cinebook.controller;

import com.charan.cinebook.models.Screen;
import com.charan.cinebook.models.Theatre;
import com.charan.cinebook.repository.ScreenRepository;
import com.charan.cinebook.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
public class ScreenController {

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @PostMapping
    public Screen create(@RequestBody Screen screen) {

        Theatre theatre = theatreRepository.findById(screen.getTheatre().getId())
                .orElseThrow(() -> new RuntimeException("Theatre Not Found"));
        screen.setTheatre(theatre);
        return screenRepository.save(screen);
    }

    @GetMapping
    public List<Screen> getAll() {
        return screenRepository.findAll();
    }

    @GetMapping("/{id}")
    public Screen getById(@PathVariable Long id) {
        return screenRepository.findById(id).orElseThrow(() -> new RuntimeException("Screen Not Found"));
    }

    @PutMapping("/{id}")
    public Screen update(@PathVariable Long id,@RequestBody Screen update) {
        Screen screen = screenRepository.findById(id).orElseThrow(() -> new RuntimeException("Screen Not Found"));

        Theatre theatre = theatreRepository.findById(update.getTheatre().getId())
                        .orElseThrow(() -> new RuntimeException("Theatre Not Found"));

        screen.setTheatre(theatre);
        screen.setName(update.getName());
        screen.setTheatre(update.getTheatre());
        return screenRepository.save(screen);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        screenRepository.deleteById(id);
    }
}
