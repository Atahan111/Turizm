package com.example.Turizm.repository;

import com.example.Turizm.entity.BookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRequestRepository extends JpaRepository<BookingRequest,Long> {
}
