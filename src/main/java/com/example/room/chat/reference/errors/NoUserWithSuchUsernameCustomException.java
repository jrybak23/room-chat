package com.example.room.chat.reference.errors;

import com.example.room.chat.reference.errors.core.AbstractCustomException;
import org.springframework.http.HttpStatus;

/**
 * Created by igorek2312 on 10.02.17.
 */
public class NoUserWithSuchUsernameCustomException extends AbstractCustomException {
    @Override
    public int getCode() {
        return 2;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getDescription() {
        return "No user with such username";
    }

}
