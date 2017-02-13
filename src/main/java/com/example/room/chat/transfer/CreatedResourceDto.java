package com.example.room.chat.transfer;

/**
 * Transfer object, contains id to send in response of POST request.
 * @author Igor Rybak
 */
public class CreatedResourceDto {
    private String id;

    public CreatedResourceDto() {
    }

    public CreatedResourceDto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
