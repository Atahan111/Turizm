package com.example.Turizm.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class TourCreateModel {

    private String name;

    private Long cost;

    private String description;

    private LocalDateTime date;

    private int duration;

    private String location;
}
