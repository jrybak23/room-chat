package com.example.room.chat.controllers;

import com.example.room.chat.domain.User;
import com.example.room.chat.reference.Constants;
import com.example.room.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by igorek2312 on 24.01.17.
 */
@RestController
@RequestMapping(Constants.URI_API)
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(Constants.URI_USERS + "/me")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public User getMyInfo() {
        User currentUser = userService.getCurrentUser();
        return currentUser;
    }

    @GetMapping(Constants.URI_USERS + "/me/authorities")
    public Collection<? extends GrantedAuthority> getMyAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    @PostMapping(Constants.URI_USERS)
    public String registerUser(@RequestBody User user) {
        return userService.createNewUser(user);
    }
}
