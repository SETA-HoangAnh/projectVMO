package com.example.project.controller;

import com.example.project.entity.Users;
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


    @GetMapping("/getUser")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> editUser(@RequestParam(required = false) String userName,
                                      @RequestParam(required = false) String fullName,
                                      @RequestParam(required = false) String codingLanguage,
                                      @RequestParam(required = false) String email){

        return userServiceImpl.getUser(userName, fullName, codingLanguage, email);
    }


    @PostMapping("/createUser")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signUpRequest) {

        return userServiceImpl.createUser(signUpRequest);
    }


    @PutMapping("/editUser/{userId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> editUser(@PathVariable("userId") Long userId, @RequestBody Users users){

        return userServiceImpl.editUser(userId, users);
    }


    @DeleteMapping("/deleteUser/{userId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId){

        return userServiceImpl.deleteUser(userId);
    }


    @PutMapping("/tranferUser/{userId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> tranferUser(@PathVariable("userId") Long userId, @RequestBody Users users){

        return userServiceImpl.tranferUser(userId, users);
    }
}
