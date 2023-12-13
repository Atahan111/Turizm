package com.example.Turizm.service.impl;

import com.example.Turizm.entity.Tour;
import com.example.Turizm.model.TourCreateModel;
import com.example.Turizm.repository.TourRepository;
import com.example.Turizm.service.TourService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;

    @Override
    public Tour create(TourCreateModel tourCreateModel) {
        Tour tour = new Tour();

        tour.setName(tourCreateModel.getName());
        tour.setDate(tourCreateModel.getDate());
        tour.setCost(tourCreateModel.getCost());
        tour.setDuration(tourCreateModel.getDuration());
        tour.setLocation(tourCreateModel.getLocation());
        tour.setDescription(tourCreateModel.getDescription());

        return tourRepository.save(tour);

    }
    @Override
    public List<Tour> getAllTours() {
        List list = tourRepository.findAll();
        if (list.isEmpty()) {
            throw new RuntimeException("Список пуст");
        }
        return list;
    }

    //поиск туров
    @Override
    public List<Tour> searchTours(String location) {
            return tourRepository.findAllByLocation(location);
    }
    @Override
    public Tour getTourById(Long tourId) {
        try {
            return tourRepository.getOne(tourId); // Попытка получить тур по идентификатору
        } catch (EntityNotFoundException e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public void deleteTour(Long tourId) {
        try {
            tourRepository.deleteById(tourId);
        } catch (EmptyResultDataAccessException ex) {
            // Обработка исключения EmptyResultDataAccessException (тур с указанным идентификатором не найден)
            // Вы можете выполнить здесь необходимые действия.
        }
    }
    @Override
    public void updateTour(Tour tour) {
        try {

            Optional<Tour> tourOptional = tourRepository.findById(tour.getId());
            if (tourOptional.isPresent()) {
                Tour newTour = tourOptional.get();
                tour.setCost(tour.getCost());
                tour.setDate(tour.getDate());
                tourRepository.save(tour);
            }
        } catch (NoSuchElementException e) {
            System.err.println("Тур с идентификатором " + tour.getId() + " не найден");
        }
    }

}
