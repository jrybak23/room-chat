package com.example.room.chat.controllers;

import com.example.room.chat.reference.errors.CustomError;
import com.example.room.chat.reference.errors.CustomErrorException;
import com.example.room.chat.reference.errors.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by igorek2312 on 26.01.17.
 */
@ControllerAdvice
public class ExceptionHandlerController {
    @Autowired
    @Qualifier("backendMessageSource")
    private MessageSource ms;

    @ExceptionHandler(CustomErrorException.class)
    @ResponseBody
    public ResponseEntity<ErrorInfo> handleErrorCodeException(CustomErrorException e) {
        CustomError error = e.getError();

        String message = error.getMessageKey().isPresent()
                ? ms.getMessage(error.getMessageKey().get(), error.getMessageArgs(), LocaleContextHolder.getLocale())
                : null;

        ErrorInfo dto = new ErrorInfo(error.getCode(), message, error.getDescription());
        return new ResponseEntity<>(dto, error.getHttpStatus());
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorInfo> handleException(AccessDeniedException e) {
        CustomError error = CustomError.ACCESS_DENIED;
        String message = error.getMessageKey().isPresent()
                ? ms.getMessage(error.getMessageKey().get(), null, LocaleContextHolder.getLocale()) : null;
        ErrorInfo dto = new ErrorInfo(error.getCode(), message, e.getMessage());
        return new ResponseEntity<>(dto, error.getHttpStatus());
    }
}
