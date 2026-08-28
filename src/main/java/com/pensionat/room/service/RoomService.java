package com.pensionat.room.service;

import com.pensionat.room.model.RoomEntity;
import com.pensionat.room.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
// innehåller affärslogik kring rum - RoomRepository via konstruktor-injection — Spring skickar
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomEntity> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<RoomEntity> getRoomById(Long roomId) {
        return roomRepository.findById(roomId);
    }

    public RoomEntity createRoom(RoomEntity room) {
        return roomRepository.save(room);
    }
}
