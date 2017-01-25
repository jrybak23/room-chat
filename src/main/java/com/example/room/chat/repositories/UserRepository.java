package com.example.room.chat.repositories;

import com.example.room.chat.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by igorek2312 on 23.01.17.
 */
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
