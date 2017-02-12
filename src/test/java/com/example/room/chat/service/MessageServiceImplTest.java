package com.example.room.chat.service;

import com.example.room.chat.domain.Message;
import com.example.room.chat.domain.Room;
import com.example.room.chat.domain.User;
import com.example.room.chat.repositories.MessageRepository;
import com.example.room.chat.repositories.RoomRepository;
import com.example.room.chat.repositories.UserRepository;
import com.example.room.chat.transfer.MessageDetails;
import com.example.room.chat.utils.SecurityUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by igorek2312 on 11.02.17.
 */
public class MessageServiceImplTest extends AbstractServiceTest {
    @Mock
    private SecurityUtils securityUtils;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoomRepository roomRepository;

    private MessageService messageService;

    @Before
    public void setUp() throws Exception {
        messageService = new MessageServiceImpl(
                securityUtils,
                messageRepository,
                userRepository,
                roomRepository
        );
    }

    @Test
    public void handleNewMessage() throws Exception {
        when(securityUtils.getCurrentUserLogin()).thenReturn("user");
        User user = new User();
        user.setUsername("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        Room room = new Room();
        when(roomRepository.findOne("roomId")).thenReturn(room);

        MessageDetails messageDetails = new MessageDetails();
        messageDetails.setUsername("foouser");
        messageDetails.setContent("cont");
        messageDetails.setDate(new Date());

        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> {
            Message message = invocation.getArgumentAt(0, Message.class);
            assertThat(message.getContent()).isEqualTo("cont");
            assertThat(message.getUser().getUsername()).isEqualTo("user");
            assertThat(message.getDate()).isNotNull();
            return message;
        });

        messageService.handleNewMessage("roomId", messageDetails);
    }

    @Test
    public void getMessages() throws Exception {
        User user = new User();
        user.setUsername("user");

        Pageable pageable = new PageRequest(0, 5);
        Message message1 = new Message();
        message1.setContent("hello");
        message1.setUser(user);
        Message message2 = new Message();
        message2.setContent("bye");
        message2.setUser(user);
        List<Message> messages = Arrays.asList(message1, message2);
        when(messageRepository.findByRoomId("room_id", pageable)).thenReturn(new PageImpl<>(messages));
        Page<MessageDetails> page = messageService.getMessages("room_id", pageable);
        assertThat(page)
                .extracting(MessageDetails::getContent)
                .containsExactly("hello", "bye");
    }

}