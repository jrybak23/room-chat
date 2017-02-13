package com.example.room.chat.service;

import com.example.room.chat.domain.User;
import com.example.room.chat.transfer.CreatedResourceDto;
import com.example.room.chat.transfer.RegistrationForm;

/**
 * @author Igor Rybak
 */
public interface UserService {
    User getCurrentUser();

    CreatedResourceDto createNewUser(RegistrationForm user);
}
