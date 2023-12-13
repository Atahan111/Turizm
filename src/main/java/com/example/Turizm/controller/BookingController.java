package com.example.Turizm.controller;

import com.example.Turizm.entity.BookingRequest;
import com.example.Turizm.model.BookingCreateModel;
import com.example.Turizm.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/reserve")
    public BookingRequest bookingReserve(@RequestBody BookingCreateModel bookingCreateModel) {
        return bookingService.create(bookingCreateModel);
    }

    @GetMapping("/getBooking/{bookingId}")
    public BookingRequest getBookingId(@PathVariable Long bookingId) {
        return bookingService.getBookingRequestById(bookingId);
    }

    @PostMapping("/cancel/{bookingRequestId}")
    public ResponseEntity<String> cancelBookingRequest(@PathVariable Long bookingRequestId) {
        try {
            bookingService.cancelBookingRequest(bookingRequestId);
            return ResponseEntity.ok("Заявка на бронирование успешно отменена");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Заявка на бронирование не найдена");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка при отмене заявки");
        }
    }

    @PostMapping("/confirm/{bookingRequestId}")
    public ResponseEntity<String> confirmBookingRequest(@PathVariable Long bookingRequestId) {
        try {
            bookingService.confirmBookingRequest(bookingRequestId);
            return ResponseEntity.ok("Заявка на бронирование успешно подтверждена");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Заявка на бронирование не найдена");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла ошибка при подтверждении заявки");
        }
    }


}
