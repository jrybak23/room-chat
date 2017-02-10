package com.example.room.chat.reference.errors.core;

import org.springframework.http.HttpStatus;

/**
 * Created by igorek2312 on 10.02.17.
 */
public class NoEntityWithSuchIdCustomException extends AbstractCustomException {
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
