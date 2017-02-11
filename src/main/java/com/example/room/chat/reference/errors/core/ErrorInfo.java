package com.example.room.chat.reference.errors.core;

/**
 * Created by igorek2312 on 26.01.17.
 */
public class ErrorInfo {
    private int errorCode;

    private String message;

    private String description;

    public ErrorInfo(int errorCode, String message, String description) {
        this.errorCode = errorCode;
        this.message = message;
        this.description = description;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
