package com.example.room.chat.config;

import com.example.room.chat.reference.errors.core.AccessDeniedCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by igorek2312 on 05.02.17.
 */
@Component
@Qualifier("stompInterceptor")
public class StompInterceptor extends ChannelInterceptorAdapter {
    @Autowired
    private TokenStore tokenStore;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String accessToken = Optional.ofNullable(accessor.getNativeHeader("Authorization"))
                    .map(list -> list.get(0))
                    .orElseThrow(AccessDeniedCustomException::new)
                    .replace("Bearer ", "").trim();

            OAuth2Authentication authentication = tokenStore.readAuthentication(accessToken);
            accessor.setUser(authentication);
        }

        return message;
    }
}
