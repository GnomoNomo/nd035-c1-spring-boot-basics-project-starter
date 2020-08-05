//package com.udacity.jwdnd.course1.cloudstorage.service;
//
//import com.udacity.jwdnd.course1.cloudstorage.entity.User;
//import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
//import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
//import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
//public class AuthenticationServiceTest {
//    private AuthenticationService sut;
//
//    @Mock
//    private Authentication authentication;
//
//    @Mock
//    private UserMapper userMapper;
//
//    @Mock
//    private HashService hashService;
//
//    private static final String CORRECT_USER_NAME = "CorrectUserName";
//    private static final String CORRECT_SALT = "CorrectSalt";
//    private static final String CORRECT_PASSWORD = "CorrectPassword";
//    private static final String INCORRECT_PASSWORD = "IncorrectPassword";
//    private static final String CORRECT_ENCRYPTED_PASSWORD = "CorrectPassword";
//    private static final String INCORRECT_ENCRYPTED_PASSWORD = "IncorrectPassword";
//
//    @BeforeEach
//    public void initialize(){
//        sut = new AuthenticationService(userMapper, hashService);
//    }
//
//    @Test
//    public void ShouldReturnValidTokenWhenAuthenticationCorrect(){
//        when(authentication.getName()).thenReturn(CORRECT_USER_NAME);
//        when(authentication.getCredentials()).thenReturn(CORRECT_PASSWORD);
//        when(userMapper.getUser(CORRECT_USER_NAME)).thenReturn(new User(null, CORRECT_USER_NAME, CORRECT_SALT, CORRECT_ENCRYPTED_PASSWORD, "",""));
//        when(hashService.getHashedValue(CORRECT_PASSWORD, CORRECT_SALT)).thenReturn(CORRECT_ENCRYPTED_PASSWORD);
//        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) sut.authenticate(authentication);
//        assertAll(
//                () -> assertEquals(token.getName(), CORRECT_USER_NAME),
//                () -> assertEquals(token.getCredentials(), CORRECT_ENCRYPTED_PASSWORD));
//    }
//
//    @Test
//    public void ShouldReturnNullTokenWhenAuthenticationCorrect(){
//        when(authentication.getName()).thenReturn(CORRECT_USER_NAME);
//        when(authentication.getCredentials()).thenReturn(INCORRECT_PASSWORD);
//        when(userMapper.getUser(CORRECT_USER_NAME)).thenReturn(new User(null, CORRECT_USER_NAME, CORRECT_SALT, CORRECT_ENCRYPTED_PASSWORD, "",""));
//        when(hashService.getHashedValue(INCORRECT_PASSWORD, CORRECT_SALT)).thenReturn(INCORRECT_ENCRYPTED_PASSWORD);
//        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) sut.authenticate(authentication);
//        assertNull(token);
//    }
//
//    @Test
//    public void shouldReturnCorrectSupport(){
//        Class<?> thing = UsernamePasswordAuthenticationToken.class;
//        boolean result = sut.supports(thing);
//        assertTrue(result);
//    }
//}
