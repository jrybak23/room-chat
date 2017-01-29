package com.example.room.chat.controllers;

import com.example.room.chat.domain.Room;
import com.example.room.chat.reference.Constants;
import com.example.room.chat.service.RoomService;
import com.example.room.chat.transfer.RoomForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping(Constants.URI_USERS + "/me" + Constants.URI_ROOMS)
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public String createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @GetMapping(Constants.URI_USERS + "/me" + Constants.URI_ROOMS)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<RoomForm> getCurrentUserRooms() {
        return roomService.getCurrentUserRooms();
    }

    @PutMapping(value = Constants.URI_USERS + "/me" + Constants.URI_ROOMS + "/{roomId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoom(@PathVariable String roomId, @RequestBody RoomForm roomForm) {
        roomService.updateRoom(roomId, roomForm);
    }

    @DeleteMapping(value = Constants.URI_USERS + "/me" + Constants.URI_ROOMS + "/{roomId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable String roomId) {
        roomService.deleteRoom(roomId);
    }
}
