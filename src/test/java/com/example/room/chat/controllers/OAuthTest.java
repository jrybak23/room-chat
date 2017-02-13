package com.example.room.chat.controllers;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * @author Igor Rybak
 */
public class OAuthTest extends AbstractControllerTest {

    @Test
    public void getAccessToken() throws Exception {
        String accessToken = super.getAccessToken("user", "password");
        assertFalse(accessToken.isEmpty());
    }

}
