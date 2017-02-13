package com.example.room.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author Igor Rybak
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
