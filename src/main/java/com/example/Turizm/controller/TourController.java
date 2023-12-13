package com.example.Turizm.controller;

import com.example.Turizm.entity.Tour;
import com.example.Turizm.model.TourCreateModel;
import com.example.Turizm.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    @PostMapping("/create")
    public ResponseEntity<Tour> createTour(@RequestBody TourCreateModel tourCreateModel) {
        return ResponseEntity.ok(tourService.create(tourCreateModel));
    }

    @GetMapping("/get-all-tour")
    public ResponseEntity<List<Tour>> getAll() {
        return ResponseEntity.ok(tourService.getAllTours());
    }

    @GetMapping("/search-tour/{location}")
    public ResponseEntity<List<Tour>> getAll(@PathVariable String location) {
        return ResponseEntity.ok(tourService.searchTours(location));
    }
}
