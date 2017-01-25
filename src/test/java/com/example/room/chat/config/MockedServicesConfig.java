package com.example.room.chat.config;

import com.example.room.chat.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

/**
 * Created by igorek2312 on 24.01.17.
 */
@Configuration
public class MockedServicesConfig {
    @Bean
    public UserService userService() {
        return mock(UserService.class);
    }
}
