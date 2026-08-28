package com.pensionat.room.service;

import com.pensionat.booking.model.BookingStatus;
import com.pensionat.booking.repository.BookingRepository;
import com.pensionat.exception.BadRequestException;
import com.pensionat.room.model.RoomEntity;
import com.pensionat.room.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

//talar om för Spring att detta är en service
@Service
public class RoomAvailabilityService {

    //tar in två repositories
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    //Konstruktor-injection — Spring skickar in båda automatiskt
    public RoomAvailabilityService(RoomRepository roomRepository,
                                   BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }
    // validering
    public List<RoomEntity> findAvailableRooms(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new BadRequestException("Start and end dates are required");
        }
        if (!endDate.isAfter(startDate)) {
            throw new BadRequestException("End date must be after start date");
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new BadRequestException("Start date cannot be in the past");
        }

        //roomRepository.findAll() — hämta alla rum från databasen
        //.stream() — gör om listan till en ström så vi kan filtrera den
        //.filter(room -> ...) — behåll bara de rum som klarar villkoret
        //!bookingRepository.existsBy... — villkoret: rummet ska inte ha någon krockande aktiv bokning
        //.toList() — samla de kvarvarande (lediga) rummen i en ny lista
        return roomRepository.findAll().stream()
                .filter(room -> !bookingRepository
                        .existsByRoomIdAndBookingStatusAndStartDateBeforeAndEndDateAfter(
                                room.getId(),
                                BookingStatus.ACTIVE,
                                endDate,
                                startDate
                        ))
                .toList();
    }
}