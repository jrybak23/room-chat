package com.example.room.chat.service;

import com.example.room.chat.transfer.MessageDetails;
import com.example.room.chat.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by igorek2312 on 04.02.17.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public void handleNewMessage(MessageDetails message) {
        message.setUsername(securityUtils.getCurrentUserLogin());
        message.setDate(new Date());
    }

    @Override
    public Page<MessageDetails> viewMessages(Pageable pageable) {
        return null;
    }
}
