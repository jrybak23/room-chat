package com.example.room.chat.service;

import com.example.room.chat.transfer.MessageDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Igor Rybak
 */
public interface MessageService {
    void handleNewMessage(String roomId, MessageDetails message);

    Page<MessageDetails> getMessages(String roomId, Pageable pageable);
}
