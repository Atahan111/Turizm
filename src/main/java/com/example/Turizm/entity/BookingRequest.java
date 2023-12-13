package com.example.Turizm.entity;

import com.example.Turizm.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BookingRequest extends BaseEntity {

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "name_contact")
    private String nameOfContact;

    @Column(name = "number_people")
    private int numberOfPeople;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Tour tour;
}