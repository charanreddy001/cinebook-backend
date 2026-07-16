package com.charan.cinebook.controller;

import com.charan.cinebook.models.Screen;
import com.charan.cinebook.repository.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
public class ScreenController {

    @Autowired
    private ScreenRepository screenRepository;

    @PostMapping
    public Screen create(@RequestBody Screen screen) {
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
        screen.setName(update.getName());
        screen.setTheatre(update.getTheatre());
        return screenRepository.save(screen);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        screenRepository.deleteById(id);
    }
}
