package com.example.room.chat.repositories;

import com.example.room.chat.domain.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by igorek2312 on 29.01.17.
 */
public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByUserId(String id);
}
