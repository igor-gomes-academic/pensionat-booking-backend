package com.pensionat.room.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "room")
public class RoomEntity {
// JPA-entitet som är mappad mot databasen.
    public RoomEntity() {
    }

    @Id //primär nyckel och genererar id automatiskt
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // mappade som columner
    private int roomNumber;

    @Enumerated(EnumType.STRING) //säger till Hibernate att spara texten "SINGLE"/"DOUBLE" i databasen
    @Column(nullable = false)
    private RoomType roomType;

    @Min(1)
    @Column(nullable = false)
    private int beds;

    @Min(0)
    @Column(nullable = false)
    private int pricePerNight;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String photoUrl;
}