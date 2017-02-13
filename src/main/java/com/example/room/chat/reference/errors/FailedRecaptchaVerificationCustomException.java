package com.example.room.chat.reference.errors;

import com.example.room.chat.reference.errors.core.CustomException;

/**
 * Thrown when failed recaptcha response verification.
 *
 * @author Igor Rybak
 */
public class FailedRecaptchaVerificationCustomException extends CustomException {
    @Override
    public int getCode() {
        return 3;
    }

    @Override
    public String getDescription() {
        return "Failed recaptcha verification";
    }
}
