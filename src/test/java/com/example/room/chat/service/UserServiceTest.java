package com.example.room.chat.service;

import com.example.room.chat.domain.User;
import com.example.room.chat.reference.errors.CustomErrorException;
import com.example.room.chat.repositories.UserRepository;
import com.example.room.chat.utils.SecurityUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by igorek2312 on 24.01.17.
 */
public class UserServiceTest extends AbstractServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityUtils securityUtils;

    @Before
    public void setUp() throws Exception {
        userService = new UserServiceImpl(userRepository, securityUtils);
    }

    @Test
    public void getCurrentUser() throws Exception {
        User user = new User();
        user.setId("qwerty123456");
        user.setUsername("user");
        user.setPassword("password");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(securityUtils.getCurrentUserLogin()).thenReturn("user");

        User currentUser = userService.getCurrentUser();
        assertEquals("user", currentUser.getUsername());
        assertEquals("", currentUser.getPassword());
    }

    @Test
    public void createNewUser() throws Exception {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");

        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            User userArg = (User) args[0];
            userArg.setId("qwerty123456");
            return userArg;
        }).when(userRepository).save(any(User.class));
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        String id = userService.createNewUser(user);
        assertEquals("qwerty123456", id);
        verify(userRepository).save(eq(user));
    }

    @Test(expected = CustomErrorException.class)
    public void createNewConflictUser() throws Exception {
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(new User()));
        User user = new User();
        user.setUsername("user");
        userService.createNewUser(user);
    }
}