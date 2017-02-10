package com.example.room.chat.service;

import com.example.room.chat.transfer.RecaptchaVerificationResponseDto;

/**
 * Created by igorek2312 on 10.02.17.
 */
public interface ReCaptchaClient {
    RecaptchaVerificationResponseDto verify(String recaptchaResponse);
}
