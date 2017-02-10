package com.example.room.chat.reference.errors;

import com.example.room.chat.reference.errors.core.AbstractCustomException;

/**
 * Created by igorek2312 on 10.02.17.
 */
public class FailedRecaptchaVerificationCustomException extends AbstractCustomException {
    @Override
    public int getCode() {
        return 3;
    }

    @Override
    public String getDescription() {
        return "Failed recaptcha verification";
    }
}
