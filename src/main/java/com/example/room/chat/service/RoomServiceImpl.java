package com.example.room.chat.service;

import com.example.room.chat.domain.Room;
import com.example.room.chat.domain.User;
import com.example.room.chat.reference.errors.NoUserWithSuchUsernameCustomException;
import com.example.room.chat.reference.errors.core.ForbiddenCustomException;
import com.example.room.chat.repositories.RoomRepository;
import com.example.room.chat.repositories.UserRepository;
import com.example.room.chat.transfer.CreatedResourceDto;
import com.example.room.chat.transfer.RoomDetail;
import com.example.room.chat.transfer.RoomForm;
import com.example.room.chat.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.room.chat.utils.EntityUtil.findOneOrThrowNotFound;

/**
 * Created by igorek2312 on 29.01.17.
 */
@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    public RoomServiceImpl(
            RoomRepository roomRepository,
            UserRepository userRepository,
            SecurityUtils securityUtils
    ) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public CreatedResourceDto createRoom(Room room) {
        User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin())
                .orElseThrow(NoUserWithSuchUsernameCustomException::new);
        room.setUser(user);
        roomRepository.save(room);
        return new CreatedResourceDto(room.getId());
    }

    private RoomDetail mapToRoomDetail(Room room) {
        RoomDetail roomDetail = new RoomDetail();
        roomDetail.setId(room.getId());
        roomDetail.setName(room.getName());
        roomDetail.setOwner(room.getUser().getUsername());
        String currentUserLogin = securityUtils.getCurrentUserLogin();
        roomDetail.setCurrentUserIsOwner(room.getUser().getUsername().equals(currentUserLogin));
        return roomDetail;
    }

    @Override
    public List<RoomDetail> getCurrentUserRooms() {
        User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin())
                .orElseThrow(NoUserWithSuchUsernameCustomException::new);

        return roomRepository.findByUserId(user.getId()).stream()
                .map(this::mapToRoomDetail)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDetail getRoom(String roomId) {
        Room room = findOneOrThrowNotFound(roomRepository, roomId, Room.class);
        authorizeRoom(room);
        return mapToRoomDetail(room);
    }

    @Override
    public void updateRoom(String roomId, RoomForm roomForm) {
        Room room = findOneOrThrowNotFound(roomRepository, roomId, Room.class);
        authorizeRoom(room);
        room.setName(roomForm.getName());
        roomRepository.save(room);
    }

    @Override
    public void deleteRoom(String roomId) {
        Room room = findOneOrThrowNotFound(roomRepository, roomId, Room.class);
        authorizeRoom(room);
        roomRepository.delete(room);
    }

    private void authorizeRoom(Room room) {
        User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin())
                .orElseThrow(NoUserWithSuchUsernameCustomException::new);

        if (!room.getUser().getId().equals(user.getId()))
            throw new ForbiddenCustomException();
    }
}
