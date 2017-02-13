package com.example.room.chat.repositories;

import com.example.room.chat.domain.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Igor Rybak
 */
public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByUserId(String id);
}
