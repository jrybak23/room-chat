package com.example.room.chat.service;

import com.example.room.chat.domain.Room;
import com.example.room.chat.transfer.RoomDetail;
import com.example.room.chat.transfer.RoomForm;

import java.util.List;

/**
 * Created by igorek2312 on 29.01.17.
 */
public interface RoomService {
    String createRoom(Room room);

    List<RoomDetail> getCurrentUserRooms();

    RoomDetail getRoom(String roomId);

    void deleteRoom(String roomId);

    void updateRoom(String roomId, RoomForm roomForm);
}
