package com.example.room.chat.reference.errors.core;

import org.springframework.http.HttpStatus;

/**
 * Created by igorek2312 on 10.02.17.
 */
public class ForbiddenCustomException extends AbstractCustomException {
    @Override
    public int getCode() {
        return 403;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public String getDescription() {
        return HttpStatus.FORBIDDEN.getReasonPhrase();
    }
}
