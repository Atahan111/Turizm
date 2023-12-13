package com.example.Turizm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingCreateModel {

    private String phoneNumber;

    private String nameOfContact;

    private int numberOfPeople;

    private Long tourId;
}
