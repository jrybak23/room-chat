package com.example.room.chat.service;

import com.example.room.chat.domain.User;

/**
 * Created by igorek2312 on 24.01.17.
 */
public interface UserService {
    User getCurrentUser();

    String createNewUser(User user);
}
