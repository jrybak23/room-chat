package com.example.room.chat.service;

import com.example.room.chat.transfer.RecaptchaVerificationResponseDto;

/**
 * @author Igor Rybak
 */
public interface ReCaptchaClient {
    RecaptchaVerificationResponseDto verify(String recaptchaResponse);
}
