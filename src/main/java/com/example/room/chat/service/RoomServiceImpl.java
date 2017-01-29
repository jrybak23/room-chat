package com.example.room.chat.service;

import com.example.room.chat.domain.Room;
import com.example.room.chat.domain.User;
import com.example.room.chat.reference.errors.CustomError;
import com.example.room.chat.reference.errors.CustomErrorException;
import com.example.room.chat.repositories.RoomRepository;
import com.example.room.chat.repositories.UserRepository;
import com.example.room.chat.transfer.RoomForm;
import com.example.room.chat.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
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
    public String createRoom(Room room) {
        User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin())
                .orElseThrow(() -> new CustomErrorException(CustomError.NO_USER_WITH_SUCH_USERNAME));
        room.setUser(user);
        roomRepository.save(room);
        return room.getId();
    }

    private Function<Room, RoomForm> mapToRoomDetail = room -> {
        RoomForm roomForm = new RoomForm();
        roomForm.setName(room.getName());
        return roomForm;
    };

    @Override
    public List<RoomForm> getCurrentUserRooms() {
        User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin())
                .orElseThrow(() -> new CustomErrorException(CustomError.NO_USER_WITH_SUCH_USERNAME));

        return roomRepository.findByUserId(user.getId()).stream()
                .map(mapToRoomDetail)
                .collect(Collectors.toList());
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
        userRepository.delete(roomId);
    }

    private void authorizeRoom(Room room) {
        User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin())
                .orElseThrow(() -> new CustomErrorException(CustomError.NO_USER_WITH_SUCH_USERNAME));

        if (!room.getUser().getId().equals(user.getId()))
            throw new CustomErrorException(CustomError.FORBIDDEN);
    }
}
