package com.example.room.chat.controllers;

import com.example.room.chat.service.MessageService;
import com.example.room.chat.transfer.MessageDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.example.room.chat.reference.Constants.*;

/**
 * Created by igorek2312 on 04.02.17.
 */
@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate template;

    private MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @MessageMapping("/chat/rooms/{room}")
    public void send(@DestinationVariable String room, MessageDetails message) throws Exception {
        messageService.handleNewMessage(room, message);
        template.convertAndSend("/topic/rooms/" + room, message);
    }

    private Pageable decoratePageable(Pageable pageable, int adjustOffset) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "date"));
        return (Pageable) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{Pageable.class},
                (o, method, objects) -> {
                    if (method.getName().equals("getOffset"))
                        return pageable.getOffset() + adjustOffset;
                    else if (method.getName().equals("getSort"))
                        return sort;
                    return method.invoke(pageable, objects);
                }
        );
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = URI_API + URI_ROOMS + "/{roomId}" + URI_MESSAGES)
    @ResponseBody
    public Page<MessageDetails> getMessages(
            @PathVariable String roomId,
            @RequestParam(defaultValue = "0") int offset,
            Pageable pageable
    ) {
        return messageService.getMessages(roomId, decoratePageable(pageable, offset));
    }

    @MessageExceptionHandler
    @SendToUser("/queue/error")
    public Exception handleException(Exception ex) {
        return ex;
    }
}
