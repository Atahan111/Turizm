package com.example.Turizm.service.impl;

import com.example.Turizm.entity.BookingRequest;
import com.example.Turizm.entity.Tour;
import com.example.Turizm.enums.Status;
import com.example.Turizm.exception.NotFoundException;
import com.example.Turizm.model.BookingCreateModel;
import com.example.Turizm.repository.BookingRequestRepository;
import com.example.Turizm.repository.TourRepository;
import com.example.Turizm.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRequestRepository bookingRequestRepository;
    private final TourRepository tourRepository;

    @Override
    public BookingRequest create(BookingCreateModel bookingCreateModel) {

        BookingRequest bookingRequest = new BookingRequest();

        bookingRequest.setPhoneNumber(bookingCreateModel.getPhoneNumber());
        bookingRequest.setNameOfContact(bookingCreateModel.getNameOfContact());
        bookingRequest.setNumberOfPeople(bookingCreateModel.getNumberOfPeople());
        bookingRequest.setStatus(Status.PENDING);

        Tour tour = tourRepository.findById(bookingCreateModel.getTourId()).orElseThrow(
                () -> new NotFoundException("Тур не найден с id = " + bookingCreateModel.getTourId())
        );
        bookingRequest.setTour(tour);
        return bookingRequestRepository.save(bookingRequest);
    }

    @Override
    public BookingRequest getBookingRequestById(Long bookingRequestId) {
        return bookingRequestRepository.findById(bookingRequestId).orElseThrow(
                () -> new NoSuchElementException("Не найдено"));
    }

    @Override
    public void confirmBookingRequest(Long bookingRequestId) {

    }


    //подтвердить запрос на бронирование


    //запрос на отмену бронирования
    @Override
    public void cancelBookingRequest(Long bookingRequestId) {
        BookingRequest bookingRequest = bookingRequestRepository.findById(bookingRequestId)
                .orElseThrow(() -> new NoSuchElementException("Заявка на бронирование не найдена"));
        bookingRequest.setStatus(Status.CANCELLED);
        bookingRequestRepository.save(bookingRequest);
    }
//обновить запрос на Бронирование до Ожидающего
    @Override
    public void updateBookingRequestToPending(Long bookingRequestId) {
        BookingRequest bookingRequest = bookingRequestRepository.findById(bookingRequestId).orElseThrow(
                () -> new NoSuchElementException("Не найдено"));
        if (bookingRequest != null) {
            bookingRequest.setStatus(Status.PENDING);
            bookingRequestRepository.save(bookingRequest);
        }
    }
//установить статус бронирования
    @Override
    public void setBookingStatus(Long bookingId, Status status) {
        BookingRequest booking = bookingRequestRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setStatus(status);
            bookingRequestRepository.save(booking);
        }
    }
//
    @Override
    public List<BookingRequest> getAll() {
        List list = bookingRequestRepository.findAll();
        if (list.isEmpty()) {
            throw new RuntimeException("Список пуст");
        }
        return list;
    }





}
