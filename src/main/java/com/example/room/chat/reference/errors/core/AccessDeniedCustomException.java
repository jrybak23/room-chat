package com.example.room.chat.reference.errors.core;

import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * Thrown according to 401 HTTP status code in RESTful web services.
 *
 * @author Igor Rybak
 */
public class AccessDeniedCustomException extends CustomException {
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
