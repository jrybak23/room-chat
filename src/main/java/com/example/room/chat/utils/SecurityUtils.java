package com.example.room.chat.utils;

public interface SecurityUtils {
    String getCurrentUserLogin();

    boolean isAuthenticated();

    boolean isCurrentUserInRole(String authority);

}
