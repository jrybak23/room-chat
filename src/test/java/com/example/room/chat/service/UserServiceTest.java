package com.example.room.chat.service;

import com.example.room.chat.domain.User;
import com.example.room.chat.reference.errors.FailedRecaptchaVerificationCustomException;
import com.example.room.chat.reference.errors.UserWithSuchUsernameAlreadyExistsCustomException;
import com.example.room.chat.repositories.UserRepository;
import com.example.room.chat.transfer.CreatedResourceDto;
import com.example.room.chat.transfer.RecaptchaVerificationResponseDto;
import com.example.room.chat.transfer.RegistrationForm;
import com.example.room.chat.utils.SecurityUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Igor Rybak
 */
public class UserServiceTest extends AbstractServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityUtils securityUtils;

    @Mock
    private ReCaptchaClient reCaptchaClient;

    @Before
    public void setUp() throws Exception {
        Mockito.reset();
        userService = new UserServiceImpl(userRepository, securityUtils, reCaptchaClient);
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
        RecaptchaVerificationResponseDto dto = new RecaptchaVerificationResponseDto();
        dto.setSuccess(true);
        when(reCaptchaClient.verify("recaptcha_response")).thenReturn(dto);
        RegistrationForm form = new RegistrationForm();
        form.setUsername("form");
        form.setPassword("password");
        form.setRecaptchaResponse("recaptcha_response");

        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            User userArg = (User) args[0];
            userArg.setId("qwerty123456");
            return userArg;
        }).when(userRepository).save(any(User.class));
        when(userRepository.findByUsername("form")).thenReturn(Optional.empty());

        CreatedResourceDto newUser = userService.createNewUser(form);
        assertEquals("qwerty123456", newUser.getId());
    }

    @Test(expected = UserWithSuchUsernameAlreadyExistsCustomException.class)
    public void assertThatUsersWithSameUsernamesCannotBeRegistred() throws Exception {
        RecaptchaVerificationResponseDto dto = new RecaptchaVerificationResponseDto();
        dto.setSuccess(true);
        when(reCaptchaClient.verify("recaptcha_response")).thenReturn(dto);

        when(userRepository.findByUsername("form")).thenReturn(Optional.of(new User()));
        RegistrationForm form = new RegistrationForm();
        form.setUsername("form");
        form.setRecaptchaResponse("recaptcha_response");
        userService.createNewUser(form);
    }

    @Test(expected = FailedRecaptchaVerificationCustomException.class)
    public void assertThatUserWithFailedRecaptchaCannotBeRegistred() throws Exception {
        RecaptchaVerificationResponseDto dto = new RecaptchaVerificationResponseDto();
        dto.setSuccess(false);
        when(reCaptchaClient.verify("recaptcha_response")).thenReturn(dto);

        when(userRepository.findByUsername("form")).thenReturn(Optional.of(new User()));
        RegistrationForm form = new RegistrationForm();
        form.setUsername("form");
        form.setRecaptchaResponse("recaptcha_response");
        userService.createNewUser(form);
    }
}