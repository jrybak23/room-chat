package com.example.room.chat.controllers;

import com.example.room.chat.domain.Room;
import com.example.room.chat.reference.Constants;
import com.example.room.chat.service.RoomService;
import com.example.room.chat.transfer.RoomForm;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by igorek2312 on 29.01.17.
 */
public class RoomControllerTest extends AbstractControllerTest {
    @Autowired
    private RoomController roomController;

    @Mock
    private RoomService roomService;

    @Before
    public void setUp() throws Exception {
        roomController.setRoomService(roomService);
    }

    @Test
    public void createRoom() throws Exception {
        when(roomService.createRoom(any(Room.class))).thenReturn("id123456");
        String accessToken = getAccessToken("user", "password");
        mvc.perform(post(Constants.URI_API + Constants.URI_USERS + "/me" + Constants.URI_ROOMS)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"foobar\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", is("id123456")));
    }

    @Test
    public void getCurrentUserRooms() throws Exception {
        String accessToken = getAccessToken("user", "password");
        mvc.perform(get(Constants.URI_API + Constants.URI_USERS + "/me" + Constants.URI_ROOMS)
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateRoom() throws Exception {
        String accessToken = getAccessToken("user", "password");
        mvc.perform(put(Constants.URI_API + Constants.URI_USERS + "/me" + Constants.URI_ROOMS + "/id123456")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"barfoo\"}"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(roomService).updateRoom(eq("id123456"), any(RoomForm.class));
    }

    @Test
    public void deleteRoom() throws Exception {
        String accessToken = getAccessToken("user", "password");
        mvc.perform(
                delete(Constants.URI_API + Constants.URI_USERS + "/me" + Constants.URI_ROOMS + "/id123456")
                        .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(roomService).deleteRoom("id123456");
    }

}
