package com.charan.cinebook.controller;

import com.charan.cinebook.models.Theatre;
import com.charan.cinebook.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {

    @Autowired
    private TheatreRepository theatreRepository;

    @PostMapping
    public Theatre create(@RequestBody Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    @GetMapping("/{id}")
    public Theatre getById(@PathVariable Long id) {
        return theatreRepository.findById(id).orElseThrow(() -> new RuntimeException("Theatre Not Found"));
    }

    @GetMapping
    public List<Theatre> getAll() {
        return theatreRepository.findAll();
    }

    @PutMapping("/{id}")
    public Theatre update(@PathVariable Long id,@RequestBody Theatre theatre) {
        Theatre theatre1 = theatreRepository.findById(id).orElseThrow(() -> new RuntimeException("Theatre Not Found"));
        theatre1.setAddress(theatre.getAddress());
        theatre1.setCity(theatre.getCity());
        theatre1.setName(theatre.getName());
        return theatreRepository.save(theatre1);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        theatreRepository.deleteById(id);
    }

}
