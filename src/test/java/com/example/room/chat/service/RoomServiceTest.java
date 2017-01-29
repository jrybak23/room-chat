package com.example.room.chat.service;

import com.example.room.chat.domain.Room;
import com.example.room.chat.domain.User;
import com.example.room.chat.repositories.RoomRepository;
import com.example.room.chat.repositories.UserRepository;
import com.example.room.chat.transfer.RoomForm;
import com.example.room.chat.utils.SecurityUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by igorek2312 on 29.01.17.
 */
public class RoomServiceTest extends AbstractServiceTest {
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SecurityUtils securityUtils;

    @Before
    public void setUp() throws Exception {
        roomService = new RoomServiceImpl(
                roomRepository,
                userRepository,
                securityUtils
        );
    }

    @Test
    public void createRoom() throws Exception {
        Room room = new Room();
        room.setName("fooroom");

        when(securityUtils.getCurrentUserLogin()).thenReturn("user");
        User user = new User();
        user.setUsername("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            Room roomArg = (Room) args[0];
            roomArg.setId("abc123456");
            assertEquals("fooroom", roomArg.getName());
            return roomArg;
        }).when(roomRepository).save(any(Room.class));

        String id = roomService.createRoom(room);
        assertEquals("abc123456", id);
    }

    @Test
    public void updateRoom() throws Exception {
        RoomForm roomForm = new RoomForm();
        roomForm.setName("barfoo");

        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            Room roomArg = (Room) args[0];
            assertEquals("barfoo", roomArg.getName());
            return roomArg;
        }).when(roomRepository).save(any(Room.class));


        when(securityUtils.getCurrentUserLogin()).thenReturn("user");
        User user = new User();
        user.setId("id123456");
        user.setUsername("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Room room = new Room();
        room.setUser(user);
        when(roomRepository.findOne("id123456")).thenReturn(room);

        roomService.updateRoom("id123456", roomForm);
    }

    @Test
    public void deleteRoom() throws Exception {
        when(securityUtils.getCurrentUserLogin()).thenReturn("user");
        User user = new User();
        user.setId("id123456");
        user.setUsername("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Room room = new Room();
        room.setUser(user);
        when(roomRepository.findOne("id123456")).thenReturn(room);
        roomService.deleteRoom("id123456");
        verify(userRepository).delete("id123456");
    }
}