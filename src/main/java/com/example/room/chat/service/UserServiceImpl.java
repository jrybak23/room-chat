package com.example.room.chat.service;

import com.example.room.chat.domain.Role;
import com.example.room.chat.domain.User;
import com.example.room.chat.reference.errors.CustomError;
import com.example.room.chat.reference.errors.CustomErrorException;
import com.example.room.chat.repositories.UserRepository;
import com.example.room.chat.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

/**
 * Created by igorek2312 on 24.01.17.
 */
@Service
@Profile("dev")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private SecurityUtils securityUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SecurityUtils securityUtils) {
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public User getCurrentUser() {
        User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin())
                .orElseThrow(() -> new CustomErrorException(CustomError.NO_USER_WITH_SUCH_USERNAME));
        user.setPassword("");
        return user;
    }

    @Override
    public String createNewUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new CustomErrorException(CustomError.USER_WITH_SUCH_USERNAME_ALREADY_EXISTS);

        userRepository.save(user);
        user.setRoles(EnumSet.of(Role.USER));
        return user.getId();
    }

}
