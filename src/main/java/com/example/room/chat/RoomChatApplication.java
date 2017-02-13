package com.example.room.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

/**
 * @author Igor Rybak
 */
@SpringBootApplication
public class RoomChatApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC")); // set UTC time
        SpringApplication.run(RoomChatApplication.class, args);
    }
}
