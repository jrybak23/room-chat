package com.example.room.chat.controllers;

import com.example.room.chat.reference.errors.core.AbstractCustomException;
import com.example.room.chat.reference.errors.core.AccessDeniedCustomException;
import com.example.room.chat.reference.errors.core.ErrorInfo;
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

    @ExceptionHandler(AbstractCustomException.class)
    @ResponseBody
    public ResponseEntity<ErrorInfo> handleErrorCodeException(AbstractCustomException e) {

        String message = e.getMessageKey().isPresent()
                ? ms.getMessage(e.getMessageKey().get(), e.getMessageArgs(), LocaleContextHolder.getLocale())
                : null;

        ErrorInfo dto = new ErrorInfo(e.getCode(), message, e.getInterpolatedDescription());

        return new ResponseEntity<>(dto, e.getHttpStatus());
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<ErrorInfo> handleException(AccessDeniedException e) {
        AccessDeniedCustomException error = new AccessDeniedCustomException();

        String message = error.getMessageKey().isPresent()
                ? ms.getMessage(error.getMessageKey().get(), null, LocaleContextHolder.getLocale()) : null;
        ErrorInfo dto = new ErrorInfo(error.getCode(), message, e.getMessage());

        return new ResponseEntity<>(dto, error.getHttpStatus());
    }

    /*@ExceptionHandler(Exception.class)
    @ResponseBody
    public Exception handleException(Exception e) {
       return e;
    }*/
}
