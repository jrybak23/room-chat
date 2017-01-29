package com.example.room.chat.reference.errors;

import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * Created by igorek2312 on 26.01.17.
 */
public enum CustomError {
    USER_WITH_SUCH_USERNAME_ALREADY_EXISTS(
            1,
            HttpStatus.CONFLICT,
            "User with such username already exists",
            "error.user.with.such.username.exists"
    ),
    NO_USER_WITH_SUCH_USERNAME(2, HttpStatus.NOT_FOUND, "No user with such username"),
    ACCESS_DENIED(401, HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase(), "error.access.denied"),
    FORBIDDEN(403, HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.getReasonPhrase()),
    NO_ENTITY_WITH_SUCH_ID(404, HttpStatus.NOT_FOUND, "No entity %s with id %s");

    private final int code;
    private final HttpStatus httpStatus;
    private String description;
    private String messageKey = null;
    private Object[] descriptionArgs = null;
    private Object[] messageArgs = null;

    CustomError(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    CustomError(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }

    CustomError(int code, HttpStatus httpStatus, String description, String messageKey) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
        this.messageKey = messageKey;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return String.format(description, descriptionArgs);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Optional<String> getMessageKey() {
        return Optional.ofNullable(messageKey);
    }

    public void setDescriptionArgs(Object... descriptionArgs) {
        this.descriptionArgs = descriptionArgs;
    }

    public Object[] getMessageArgs() {
        return messageArgs;
    }

    public void setMessageArgs(Object... messageArgs) {
        this.messageArgs = messageArgs;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
