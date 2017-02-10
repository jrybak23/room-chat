package com.example.room.chat.reference.errors.core;

import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * Created by igorek2312 on 10.02.17.
 */
public class AccessDeniedCustomException extends AbstractCustomException {
    @Override
    public int getCode() {
        return 401;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getDescription() {
        return HttpStatus.UNAUTHORIZED.getReasonPhrase();
    }

    @Override
    public Optional<String> getMessageKey() {
        return Optional.of("error.access.denied");
    }
}
