package com.example.room.chat.reference.errors;

import com.example.room.chat.reference.errors.core.AbstractCustomException;
import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * Created by igorek2312 on 10.02.17.
 */
public class UserWithSuchUsernameAlreadyExistsCustomException extends AbstractCustomException {
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
