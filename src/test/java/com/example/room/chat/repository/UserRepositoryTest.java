package com.example.room.chat.repository;

import com.example.room.chat.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created by igorek2312 on 23.01.17.
 */
public class UserRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        importJSON("user", "src/test/resources/user.json");
    }

    @Test
    public void findByUsername() throws Exception {
        User user = userRepository.findByUsername("user").get();
        assertEquals(user.getUsername(), "user");
    }

}