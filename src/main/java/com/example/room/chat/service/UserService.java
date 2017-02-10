package com.example.room.chat.service;

import com.example.room.chat.domain.User;
import com.example.room.chat.transfer.CreatedResourceDto;
import com.example.room.chat.transfer.RegistrationForm;

/**
 * Created by igorek2312 on 24.01.17.
 */
public interface UserService {
    User getCurrentUser();

    CreatedResourceDto createNewUser(RegistrationForm user);
}
