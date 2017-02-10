package com.example.room.chat.reference.errors.core;

import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * Created by igorek2312 on 10.02.17.
 */
public abstract class AbstractCustomException extends RuntimeException {
    private Object[] descriptionArgs;
    private Object[] messageArgs = null;

    public final String getInterpolatedDescription() {
        return String.format(getDescription(), descriptionArgs);
    }

    public final void setDescriptionArgs(Object... descriptionArgs) {
        this.descriptionArgs = descriptionArgs;
    }

    public Object[] getMessageArgs() {
        return messageArgs;
    }

    public final void setMessageArgs(Object... messageArgs) {
        this.messageArgs = messageArgs;
    }

    public abstract int getCode();

    public Optional<String> getMessageKey() {
        return Optional.empty();
    }

    public abstract String getDescription();

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
