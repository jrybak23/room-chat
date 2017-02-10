package com.example.room.chat.controllers;

import com.example.room.chat.domain.Role;
import com.example.room.chat.domain.User;
import com.example.room.chat.reference.Constants;
import com.example.room.chat.service.UserService;
import com.example.room.chat.transfer.CreatedResourceDto;
import com.example.room.chat.transfer.RegistrationForm;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by igorek2312 on 24.01.17.
 */
public class UserControllerTest extends AbstractControllerTest {
    @Autowired
    private UserController userController;

    @Mock
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        Mockito.reset();
        userController.setUserService(userService);
    }

    @Test
    public void getMyInfo() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setPassword("");

        when(userService.getCurrentUser()).thenReturn(user);
        String accessToken = getAccessToken("user", "password");
        mvc.perform(get(Constants.URI_API + Constants.URI_USERS + "/me")
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("user")))
                .andExpect(jsonPath("$.password", is("")));
    }

    @Test
    public void getAuthoritiesOfUser() throws Exception {
        String accessToken = getAccessToken("user", "password");
        mvc.perform(get(Constants.URI_API + Constants.URI_USERS + "/me/authorities")
                .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is(Role.USER.getAuthority())));
    }

    @Test
    public void getAuthoritiesOfAnonymous() throws Exception {
        mvc.perform(get(Constants.URI_API + Constants.URI_USERS + "/me/authorities"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is(Role.ANONYMOUS.getAuthority())));
    }

    @Test
    public void registerUser() throws Exception {
        when(userService.createNewUser(any(RegistrationForm.class))).thenReturn(new CreatedResourceDto("abc123456"));
        mvc.perform(post(Constants.URI_API + Constants.URI_USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"username\" : \"user\",\n" +
                        "  \"password\" : \"password\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is("abc123456")));
    }

}