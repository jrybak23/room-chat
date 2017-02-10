package com.example.room.chat.controllers;

import com.example.room.chat.domain.User;
import com.example.room.chat.reference.Constants;
import com.example.room.chat.service.UserService;
import com.example.room.chat.transfer.CreatedResourceDto;
import com.example.room.chat.transfer.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by igorek2312 on 24.01.17.
 */
@RestController
@RequestMapping(Constants.URI_API)
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(Constants.URI_USERS + "/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public User getMyInfo() {
        return userService.getCurrentUser();
    }

    @GetMapping(Constants.URI_USERS + "/me/authorities")
    public List<String> getMyAuthorities() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    @PostMapping(value = Constants.URI_USERS)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResourceDto registerUser(@RequestBody RegistrationForm form) {
        return userService.createNewUser(form);
    }

}
