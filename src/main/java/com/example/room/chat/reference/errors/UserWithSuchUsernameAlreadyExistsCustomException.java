package com.example.room.chat.reference.errors;

import com.example.room.chat.reference.errors.core.CustomException;
import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * Thrown during registration new user when the user with same username already exists.
 *
 * @author Igor Rybak
 */
public class UserWithSuchUsernameAlreadyExistsCustomException extends CustomException {
    @Override
    public int getCode() {
        return 1;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getDescription() {
        return "User with such username already exists";
    }

    @Override
    public Optional<String> getMessageKey() {
        return Optional.of("error.user.with.such.username.exists");
    }
}
