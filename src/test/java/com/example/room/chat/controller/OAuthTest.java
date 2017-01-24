package com.example.room.chat.controller;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by igorek2312 on 23.01.17.
 */
public class OAuthTest extends AbstractControllerTest {

    @Test
    public void getAccessToken() throws Exception {
        String accessToken = super.getAccessToken("user", "password");
        assertFalse(accessToken.isEmpty());
    }

}
