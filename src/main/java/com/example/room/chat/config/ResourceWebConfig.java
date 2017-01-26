package com.example.room.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Created by igorek2312 on 26.01.17.
 */
@Configuration
public class ResourceWebConfig {
    @Bean
    public ReloadableResourceBundleMessageSource backendMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/Messages");
        return messageSource;
    }
}
