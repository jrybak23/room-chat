package com.example.room.chat.repositories;

import com.example.room.chat.domain.User;
import com.example.room.chat.reference.errors.core.NoEntityWithSuchIdCustomException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.room.chat.utils.EntityUtil.findOneOrThrowNotFound;
import static org.junit.Assert.assertEquals;

/**
 * Created by igorek2312 on 23.01.17.
 */
public class UserRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        mongoTemplate.getCollectionNames().forEach(mongoTemplate::dropCollection);
        importJSON("user", "src/test/resources/users.json");
    }

    @Test
    public void findByUsername() throws Exception {
        User user = userRepository.findByUsername("user").get();
        assertEquals("user", user.getUsername());
    }

    @Test(expected = NoEntityWithSuchIdCustomException.class)
    public void notFoundTry() throws Exception {
        findOneOrThrowNotFound(userRepository, "fakeid123", User.class);
    }

}