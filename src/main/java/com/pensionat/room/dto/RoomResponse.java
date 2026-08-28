package com.pensionat.room.dto;

import com.pensionat.room.model.RoomEntity;
import com.pensionat.room.model.RoomType;

//Data Transfer Object - Ren verison av vår RoomEntity som skickas till frontend
public record RoomResponse(
        Long id,
        int roomNumber,
        RoomType roomType,
        int beds,
        int pricePerNight,
        String description,
        String photoUrl
) {
    public static RoomResponse from(RoomEntity entity) {
        return new RoomResponse(
                entity.getId(),
                entity.getRoomNumber(),
                entity.getRoomType(),
                entity.getBeds(),
                entity.getPricePerNight(),
                entity.getDescription(),
                entity.getPhotoUrl()
        );
    }

    //Tar ett RoomEntity — ett rum från databasen med alla fält
    //Plockar ut varje värde — entity.getId() osv
    //Bygger ett nytt RoomResponse -
    //Returnerar DTO:n — som sedan blir JSON när controllern skickar den
}