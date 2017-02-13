package com.example.room.chat.service;

import com.example.room.chat.domain.Message;
import com.example.room.chat.domain.Room;
import com.example.room.chat.domain.User;
import com.example.room.chat.reference.errors.NoUserWithSuchUsernameCustomException;
import com.example.room.chat.repositories.MessageRepository;
import com.example.room.chat.repositories.RoomRepository;
import com.example.room.chat.repositories.UserRepository;
import com.example.room.chat.transfer.MessageDetails;
import com.example.room.chat.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.room.chat.utils.EntityUtil.findOneOrThrowNotFound;

/**
 * @author Igor Rybak
 */
@Service
public class MessageServiceImpl implements MessageService {
    private SecurityUtils securityUtils;

    private MessageRepository messageRepository;

    private UserRepository userRepository;

    private RoomRepository roomRepository;


    @Autowired
    public MessageServiceImpl(
            SecurityUtils securityUtils,
            MessageRepository messageRepository,
            UserRepository userRepository,
            RoomRepository roomRepository
    ) {
        this.securityUtils = securityUtils;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void handleNewMessage(String roomId, MessageDetails messageDetails) {
        String username = securityUtils.getCurrentUserLogin();
        messageDetails.setUsername(username);
        messageDetails.setDate(new Date());
        Message message = new Message();
        User user = userRepository.findByUsername(username)
                .orElseThrow(NoUserWithSuchUsernameCustomException::new);

        message.setUser(user);
        message.setContent(messageDetails.getContent());
        message.setDate(messageDetails.getDate());

        Room room = findOneOrThrowNotFound(roomRepository, roomId, Room.class);
        message.setRoom(room);
        messageRepository.save(message);
    }

    @Override
    public Page<MessageDetails> getMessages(String roomId, Pageable pageable) {
        return messageRepository.findByRoomId(roomId, pageable).map(this::mapToMessageDetail);
    }

    private MessageDetails mapToMessageDetail(Message message) {
        MessageDetails details = new MessageDetails();
        details.setUsername(message.getUser().getUsername());
        details.setContent(message.getContent());
        details.setDate(message.getDate());
        return details;
    }
}
