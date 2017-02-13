package com.example.room.chat.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author Igor Rybak
 */
public class MessageDetails {
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date date;

    private String username;

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
