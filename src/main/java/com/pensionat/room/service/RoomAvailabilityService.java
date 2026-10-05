package com.pensionat.room.service;

import com.pensionat.booking.model.BookingStatus;
import com.pensionat.booking.repository.BookingRepository;
import com.pensionat.exception.BadRequestException;
import com.pensionat.room.model.RoomEntity;
import com.pensionat.room.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomAvailabilityService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private static final Logger log = LoggerFactory.getLogger(RoomAvailabilityService.class);

    public RoomAvailabilityService(RoomRepository roomRepository,
                                   BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<RoomEntity> findAvailableRooms(LocalDate startDate, LocalDate endDate) {
        log.info("Searching for available rooms");
        if (startDate == null || endDate == null) {
            log.warn("Room availability search rejected: missing dates");
            throw new BadRequestException("Start and end dates are required");
        }
        if (!endDate.isAfter(startDate)) {
            log.warn("Room availability search rejected: invalid date range");
            throw new BadRequestException("End date must be after start date");
        }
        if (startDate.isBefore(LocalDate.now())) {
            log.warn("Room availability search rejected: start date is in the past");
            throw new BadRequestException("Start date cannot be in the past");
        }

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