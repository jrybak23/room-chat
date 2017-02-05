package com.example.room.chat.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by igorek2312 on 04.02.17.
 */
public class MessageDetails {
    private String id;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date date;

    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
