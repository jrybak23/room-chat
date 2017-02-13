package com.example.room.chat.config;

import com.example.room.chat.domain.Role;
import com.example.room.chat.domain.User;
import org.mockito.internal.util.collections.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Mocked user detail service {@link UserDetailsService} for MVC integration tests.
 *
 * @author Igor Rybak
 */
@Service
public class MockedCustomUserDetailsService implements UserDetailsService {
    private final Set<User> users = new HashSet<>();

    {
        User user = new User();
        user.setId("qwerty123456");
        user.setUsername("user");
        user.setPassword("password");
        user.setRoles(Sets.newSet(Role.USER));
        users.add(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst().orElseThrow(() -> new UsernameNotFoundException("User does not exist"));

        return new UserRepositoryUserDetails(user);
    }

    private static class UserRepositoryUserDetails extends User implements UserDetails {

        public UserRepositoryUserDetails(User user) {
            super(user);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return getRoles();
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
    }
}
