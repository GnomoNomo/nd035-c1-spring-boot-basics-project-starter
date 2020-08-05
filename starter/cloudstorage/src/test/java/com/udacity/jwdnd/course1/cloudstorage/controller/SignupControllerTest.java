package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SignupControllerTest {
    private static final String CORRECT_USERNAME = "CORRECT USER NAME";
    private static final String CORRECT_PASSWORD = "CORRECT PASSWORD";
    private static final String INCORRECT_USER_NAME = "INCORRECT USER NAME";
    private static final String INCORRECT_PASSWORD = "INCORRECT PASSWORD";
    private static final Integer USER_ID = 1;

    private SignupController sut;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @BeforeEach
    public void initialize(){
        sut = new SignupController(userService);
    }

    @Test
    public void ShouldReturnCorrectView(){
        assertEquals("signup", sut.signupView());
    }

    @Test
    public void ShouldRegisterNewUser(){
        User user = Mockito.mock(User.class);

        when(userService.isUsernameAvailable(CORRECT_USERNAME)).thenReturn(true);
        when(userService.createUser(isA(User.class))).thenReturn(USER_ID);

        when(user.getUsername()).thenReturn(CORRECT_USERNAME);
        when(user.getPassword()).thenReturn(CORRECT_PASSWORD);

        sut.registerUser(user, model);
        Mockito.verify(userService).createUser(isA(User.class));
        Mockito.verify(model).addAttribute(SignupController.SIGNUP_SUCCESS, true);
    }

    @Test
    public void ShouldReturnErrorIfUserAlreadyExists(){
        User user = Mockito.mock(User.class);

        when(userService.isUsernameAvailable(CORRECT_USERNAME)).thenReturn(false);
        when(userService.createUser(isA(User.class))).thenReturn(USER_ID);

        when(user.getUsername()).thenReturn(CORRECT_USERNAME);
        when(user.getPassword()).thenReturn(CORRECT_PASSWORD);

        sut.registerUser(user, model);
        Mockito.verify(model).addAttribute(SignupController.SIGNUP_ERROR, SignupController.ERROR_USER_EXISTS);
    }

    @Test
    public void ShouldReturnErrorIfUsernameMissing(){
        User user = Mockito.mock(User.class);

        when(userService.isUsernameAvailable(CORRECT_USERNAME)).thenReturn(true);
        when(userService.createUser(isA(User.class))).thenReturn(USER_ID);

        when(user.getUsername()).thenReturn(null);
        when(user.getPassword()).thenReturn(CORRECT_PASSWORD);

        sut.registerUser(user, model);
        Mockito.verify(model).addAttribute(SignupController.SIGNUP_ERROR, SignupController.ERROR_MISSING_USER_NAME);
    }

    @Test
    public void ShouldReturnErrorIfPasswordMissing(){
        User user = Mockito.mock(User.class);

        when(userService.isUsernameAvailable(CORRECT_USERNAME)).thenReturn(true);
        when(userService.createUser(isA(User.class))).thenReturn(USER_ID);

        when(user.getUsername()).thenReturn(CORRECT_USERNAME);
        when(user.getPassword()).thenReturn(null);

        sut.registerUser(user, model);
        Mockito.verify(model).addAttribute(SignupController.SIGNUP_ERROR, SignupController.ERROR_MISSING_PASSWORD);
    }
}
