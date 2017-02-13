package com.example.room.chat.repositories;

import com.example.room.chat.domain.Message;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Igor Rybak
 */
public class MessageRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Before
    public void setUp() throws Exception {
        mongoTemplate.getCollectionNames().forEach(mongoTemplate::dropCollection);
        importJSON("user", "src/test/resources/users.json");
        importJSON("room", "src/test/resources/rooms.json");
        importJSON("message", "src/test/resources/messages.json");
    }

    @Test
    public void findByRoomId() throws Exception {
        Pageable request = new PageRequest(0, 20);
        Page<Message> messages = messageRepository.findByRoomId("0000000000000000000000b1", request);
        assertThat(messages.getContent())
                .hasSize(2)
                .extracting(Message::getContent)
                .containsExactly("hello", "bye");
    }

}