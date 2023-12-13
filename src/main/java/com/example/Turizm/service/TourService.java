package com.example.Turizm.service;

import com.example.Turizm.entity.Tour;
import com.example.Turizm.model.TourCreateModel;

import java.util.List;

public interface TourService {

    Tour create(TourCreateModel tourCreateModel);


    List<Tour> getAllTours();

    //поиск туров
    List<Tour> searchTours(String location);

    Tour getTourById(Long tourId);

    void deleteTour(Long tourId);

    void updateTour(Tour tour);
}
