package com.example.room.chat.config;

import com.example.room.chat.domain.Role;
import com.example.room.chat.domain.User;
import com.example.room.chat.service.CustomUserDetailsService;
import org.mockito.internal.util.collections.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by igorek2312 on 23.01.17.
 */
@Configuration
public class UserDetailServiceConfig {
    /*private final UserDetails userDetails = new UserDetails() {

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Sets.newSet(Role.USER);
        }

        @Override
        public String getPassword() {
            return "password";
        }

        @Override
        public String getUsername() {
            return "user";
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    };*/

    @Bean
    public CustomUserDetailsService userDetailsService() {
        CustomUserDetailsService mock = mock(CustomUserDetailsService.class);
        User user = new User();
        user.setId("qwerty123456");
        user.setUsername("user");
        user.setPassword("password");
        user.setRoles(Sets.newSet(Role.USER));
        CustomUserDetailsService.CustomUserDetails userDetails = new CustomUserDetailsService.CustomUserDetails(user);
        when(mock.loadUserByUsername("user")).thenReturn(userDetails);
        return mock;
    }
}
