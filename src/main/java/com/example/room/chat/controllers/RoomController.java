package com.example.room.chat.controllers;

import com.example.room.chat.domain.Room;
import com.example.room.chat.reference.Constants;
import com.example.room.chat.service.RoomService;
import com.example.room.chat.transfer.CreatedResourceDto;
import com.example.room.chat.transfer.RoomDetail;
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

    @PostMapping(value = Constants.URI_USERS + "/me" + Constants.URI_ROOMS)
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResourceDto createRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    @GetMapping(Constants.URI_USERS + "/me" + Constants.URI_ROOMS)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<RoomDetail> getCurrentUserRooms() {
        return roomService.getCurrentUserRooms();
    }

    @GetMapping(Constants.URI_USERS + "/me" + Constants.URI_ROOMS + "/{roomId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public RoomDetail getRoom(@PathVariable String roomId) {
        return roomService.getRoom(roomId);
    }

    @PutMapping(Constants.URI_USERS + "/me" + Constants.URI_ROOMS + "/{roomId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRoom(@PathVariable String roomId, @RequestBody RoomForm roomForm) {
        roomService.updateRoom(roomId, roomForm);
    }

    @DeleteMapping(Constants.URI_USERS + "/me" + Constants.URI_ROOMS + "/{roomId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoom(@PathVariable String roomId) {
        roomService.deleteRoom(roomId);
    }
}
