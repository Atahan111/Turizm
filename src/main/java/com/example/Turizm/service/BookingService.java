package com.example.Turizm.service;

import com.example.Turizm.entity.BookingRequest;
import com.example.Turizm.enums.Status;
import com.example.Turizm.model.BookingCreateModel;

import java.util.List;

public interface BookingService {

    BookingRequest create(BookingCreateModel bookingCreateModel);

    BookingRequest getBookingRequestById(Long bookingRequestId);

    void confirmBookingRequest(Long bookingRequestId);

    //запрос на отмену бронирования
    void cancelBookingRequest(Long bookingRequestId);

    void updateBookingRequestToPending(Long bookingRequestId);

    void setBookingStatus(Long bookingId, Status status);

    List<BookingRequest> getAll();
}
