package com.example.room.chat.transfer;

/**
 * Created by igorek2312 on 10.02.17.
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
