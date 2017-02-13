package com.example.room.chat.reference.errors.core;

import org.springframework.http.HttpStatus;

/**
 * Thrown according to 404 HTTP status code in RESTful web services. When the requested entity by id is not found.
 *
 * @author Igor Rybak
 */
public class NoEntityWithSuchIdCustomException extends CustomException {
    @Override
    public int getCode() {
        return 404;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getDescription() {
        return "No entity %s with id %s";
    }
}
