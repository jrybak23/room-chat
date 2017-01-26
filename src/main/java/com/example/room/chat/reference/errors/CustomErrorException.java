package com.example.room.chat.reference.errors;

/**
 * Created by igorek2312 on 26.01.17.
 */
public class CustomErrorException extends IllegalArgumentException {
    private CustomError error;

    public CustomErrorException(CustomError customError) {
        super(customError.getDescription());
        this.error = customError;
    }

    public CustomErrorException(String message, CustomError customError) {
        super(message);
        customError.setDescription(message);
        this.error = customError;
    }

    public CustomError getError() {
        return error;
    }
}