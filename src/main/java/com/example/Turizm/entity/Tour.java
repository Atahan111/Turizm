package com.example.Turizm.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Tour extends BaseEntity {

    private String name;

    private Long cost;

    private String description;

    private LocalDateTime date;

    private int duration;

    private String location;
}
