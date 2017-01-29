package com.example.room.chat.repositories;

import com.example.room.chat.domain.Room;
import com.example.room.chat.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by igorek2312 on 29.01.17.
 */
public class RoomRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private RoomRepository roomRepository;

    @Before
    public void setUp() throws Exception {
        importJSON("user", "src/test/resources/users.json");
        importJSON("room", "src/test/resources/rooms.json");
    }

    @Test
    public void findByUserUsername() throws Exception {
        List<Room> rooms = roomRepository.findByUserId("0000000000000000000000a1");
        assertThat(rooms)
                .hasSize(1)
                .extracting(Room::getUser)
                .extracting(User::getUsername)
                .containsExactly("user");
    }
}