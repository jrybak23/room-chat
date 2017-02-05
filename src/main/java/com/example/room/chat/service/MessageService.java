package com.example.room.chat.service;

import com.example.room.chat.transfer.MessageDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by igorek2312 on 04.02.17.
 */
public interface MessageService {
    void handleNewMessage(MessageDetails message);

    Page<MessageDetails> viewMessages(Pageable pageable);
}
