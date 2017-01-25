package com.example.room.chat.utils;

/**
 * Created by igorek2312 on 24.01.17.
 */
public interface SecurityUtils {
    String getCurrentUserLogin();

    boolean isAuthenticated();

    boolean isCurrentUserInRole(String authority);

}
