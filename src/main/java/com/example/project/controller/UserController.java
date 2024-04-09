package com.example.project.controller;

import com.example.project.payload.SignupRequest;
import com.example.project.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${apiPrefix}/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @PostMapping("/createUser")
    @PreAuthorize("@userServiceImpl.getRoles().contains('ROLE_ADMIN') " +
            "|| @userServiceImpl.getRoles().contains('ROLE_MANAGER') ")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signUpRequest) {

        return userServiceImpl.createUser(signUpRequest);
    }
}
