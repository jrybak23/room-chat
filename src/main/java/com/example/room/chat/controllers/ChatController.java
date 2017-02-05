package com.example.room.chat.controllers;

import com.example.room.chat.service.MessageService;
import com.example.room.chat.transfer.MessageDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

/**
 * Created by igorek2312 on 04.02.17.
 */
@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private MessageService messageService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @MessageMapping("/chat/rooms/{room}")
    public void send(@DestinationVariable String room, MessageDetails message) throws Exception {
        messageService.handleNewMessage(message);
        template.convertAndSend("/topic/rooms/" + room, message);
    }
}
