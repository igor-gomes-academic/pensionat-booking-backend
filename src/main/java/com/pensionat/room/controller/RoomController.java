package com.pensionat.room.controller;

import com.pensionat.room.dto.RoomResponse;
import com.pensionat.room.model.RoomEntity;
import com.pensionat.room.service.RoomAvailabilityService;
import com.pensionat.room.service.RoomService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


//Controllern tar emot datumen
//Anropar roomAvailabilityService.findAvailableRooms(...)
//listar lediga rum
//Mappar till RoomResponse och returnerar som JSON

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final RoomAvailabilityService roomAvailabilityService;


    public RoomController(RoomService roomService, RoomAvailabilityService roomAvailabilityService) {
        this.roomService = roomService;
        this.roomAvailabilityService = roomAvailabilityService;
    }

    @GetMapping
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms()
                .stream()
                .map(RoomResponse::from)
                .toList();
    }

    @GetMapping("/available")
    public List<RoomResponse> getAvailableRooms(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, //talar om för Spring standardformat
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return roomAvailabilityService.findAvailableRooms(startDate, endDate)
                .stream()
                .map(RoomResponse::from)
                .toList();
    }


    @PostMapping
    public RoomResponse createRoom(@RequestBody RoomEntity room) {
        RoomEntity savedRoom = roomService.createRoom(room);
        return RoomResponse.from(savedRoom);
    }
}