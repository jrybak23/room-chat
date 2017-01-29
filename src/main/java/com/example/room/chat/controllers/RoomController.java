package com.example.room.chat.controllers;

import com.example.room.chat.domain.Room;
import com.example.room.chat.reference.Constants;
import com.example.room.chat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by igorek2312 on 27.01.17.
 */
@RestController
@RequestMapping(Constants.URI_API)
public class RoomController {
    private RoomService roomService;

    @Autowired
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping(Constants.URI_ROOMS)
    public String createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }
}
