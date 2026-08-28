package com.pensionat.room.repository;

import com.pensionat.room.model.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// interface All databasåtkomst för rum går genom denna
public interface RoomRepository
        extends JpaRepository<RoomEntity, Long> {
}