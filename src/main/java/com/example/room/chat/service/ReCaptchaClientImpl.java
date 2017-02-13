package com.example.room.chat.service;

import com.example.room.chat.transfer.RecaptchaVerificationResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author Igor Rybak
 */
@Component
public class ReCaptchaClientImpl implements ReCaptchaClient {
    @Value("${recaptcha.url}")
    private String recaptchaUrl;

    @Value("${recaptcha.secret}")
    private String secretKey;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public RecaptchaVerificationResponseDto verify(String recaptchaResponse) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("secret", secretKey);
        form.add("response", recaptchaResponse);
        return restTemplate.postForObject(recaptchaUrl, form, RecaptchaVerificationResponseDto.class);
    }
}
