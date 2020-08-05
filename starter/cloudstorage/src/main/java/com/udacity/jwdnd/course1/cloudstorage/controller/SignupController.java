package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserService userService;

    public static final String SIGNUP_SUCCESS = "signupSuccess";
    public static final String SIGNUP_ERROR = "signupError";
    public static final String ERROR_USER_EXISTS = "Username already exists";
    public static final String ERROR_MISSING_USER_NAME = "Username is missing";
    public static final String ERROR_MISSING_PASSWORD = "Password is missing";

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView(){
        return "signup";
    }

    @PostMapping
    public String registerUser(User user, Model model) {
        String errorString =  null;

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            errorString = ERROR_MISSING_USER_NAME;
        } else if (user.getPassword() == null || user.getPassword().isEmpty()) {
            errorString = ERROR_MISSING_PASSWORD;
        } else if(!userService.isUsernameAvailable(user.getUsername())) {
            errorString = ERROR_USER_EXISTS;
        }

        if(errorString == null){
            userService.createUser(user);
            model.addAttribute(SIGNUP_SUCCESS, true);
        } else{
            model.addAttribute(SIGNUP_ERROR, errorString);
        }

        return "signup";
    }
}
