package com.example.room.chat.service;

import com.example.room.chat.domain.Role;
import com.example.room.chat.domain.User;
import com.example.room.chat.reference.errors.FailedRecaptchaVerificationCustomException;
import com.example.room.chat.reference.errors.NoUserWithSuchUsernameCustomException;
import com.example.room.chat.reference.errors.UserWithSuchUsernameAlreadyExistsCustomException;
import com.example.room.chat.repositories.UserRepository;
import com.example.room.chat.transfer.CreatedResourceDto;
import com.example.room.chat.transfer.RecaptchaVerificationResponseDto;
import com.example.room.chat.transfer.RegistrationForm;
import com.example.room.chat.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

/**
 * @author Igor Rybak
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final SecurityUtils securityUtils;

    private ReCaptchaClient reCaptchaClient;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            SecurityUtils securityUtils,
            ReCaptchaClient reCaptchaClient
    ) {
        this.userRepository = userRepository;
        this.securityUtils = securityUtils;
        this.reCaptchaClient = reCaptchaClient;
    }

    @Override
    public User getCurrentUser() {
        User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin())
                .orElseThrow(NoUserWithSuchUsernameCustomException::new);
        user.setPassword("");
        return user;
    }

    @Override
    public CreatedResourceDto createNewUser(RegistrationForm form) {
        RecaptchaVerificationResponseDto response = reCaptchaClient.verify(form.getRecaptchaResponse());
        if (!response.isSuccess())
            throw new FailedRecaptchaVerificationCustomException();

        if (userRepository.findByUsername(form.getUsername()).isPresent())
            throw new UserWithSuchUsernameAlreadyExistsCustomException();

        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        user.setRoles(EnumSet.of(Role.USER));
        userRepository.save(user);
        return new CreatedResourceDto(user.getId());
    }

}
