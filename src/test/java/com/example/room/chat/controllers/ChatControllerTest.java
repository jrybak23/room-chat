package com.example.room.chat.controllers;

import com.example.room.chat.service.MessageService;
import com.example.room.chat.transfer.MessageDetails;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;

import static com.example.room.chat.reference.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by igorek2312 on 11.02.17.
 */
public class ChatControllerTest extends AbstractControllerTest {
    @Autowired
    private ChatController chatController;

    @Mock
    private MessageService messageService;

    @Before
    public void setUp() throws Exception {
        chatController.setMessageService(messageService);
    }


    @Test
    public void getMessages() throws Exception {
        MessageDetails message1 = new MessageDetails();
        message1.setContent("hello");
        message1.setUsername("user");
        MessageDetails message2 = new MessageDetails();
        message2.setContent("bye");
        message2.setUsername("user");

        PageImpl<MessageDetails> page = new PageImpl<>(Arrays.asList(message1, message2));

        when(messageService.getMessages(eq("room_id"), any(Pageable.class))).thenAnswer(invocation -> {
            Pageable pageable = invocation.getArgumentAt(1, Pageable.class);
            assertThat(pageable.getPageNumber()).isEqualTo(0);
            assertThat(pageable.getPageSize()).isEqualTo(3);
            return page;
        });

        String accessToken = getAccessToken("user", "password");

        mvc.perform(
                get(
                        URI_API + URI_ROOMS + "/{roomId}" + URI_MESSAGES + "?page={page}&size={size}",
                        "room_id", 0, 3
                )
                        .header("Authorization", "Bearer " + accessToken)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*]", hasSize(2)))
                .andExpect(jsonPath("$.content[*].content", containsInAnyOrder("hello", "bye")));
    }
}