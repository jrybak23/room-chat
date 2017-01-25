package com.example.room.chat.controllers;

import com.example.room.chat.domain.Role;
import com.example.room.chat.domain.User;
import com.example.room.chat.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by igorek2312 on 24.01.17.
 */
public class UserControllerTest extends AbstractControllerTest {
    @Autowired
    private UserService userService;

    @Test
    public void getMyInfo() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setPassword("");

        when(userService.getCurrentUser()).thenReturn(user);
        String accessToken = getAccessToken("user", "password");
        mvc.perform(get(API_VERSION + "/users/me")
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(jsonPath("$.username", is("user")))
                .andExpect(jsonPath("$.password", is("")));
    }

    @Test
    public void getAuthoritiesOfUser() throws Exception {
        String accessToken = getAccessToken("user", "password");
        mvc.perform(get(API_VERSION + "/users/me/authorities")
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(jsonPath("$[0].authority", is(Role.USER.getAuthority())));
    }

    @Test
    public void getAuthoritiesOfAnonymous() throws Exception {
        mvc.perform(get(API_VERSION + "/users/me/authorities"))
                .andDo(print())
                .andExpect(jsonPath("$[0].authority", is(Role.ANONYMOUS.getAuthority())));
    }

    @Test
    public void registerUser() throws Exception {
        when(userService.createNewUser(any(User.class))).thenReturn("abc123456");
        mvc.perform(post(API_VERSION + "/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"username\" : \"user\",\n" +
                        "  \"password\" : \"password\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(jsonPath("$", is("abc123456")));
    }

}