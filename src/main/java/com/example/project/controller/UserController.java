package com.example.project.controller;

import com.example.project.dto.UserInforDto;
import com.example.project.dto.UserInforNoCenterDTO;
import com.example.project.entity.UserGrade;
import com.example.project.entity.Users;
import com.example.project.payload.SignupRequest;
import com.example.project.service.UserServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${apiPrefix}/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {

        this.userServiceImpl = userServiceImpl;
    }


    /**
     * type = 1: filter by user with center
     * type = 2: filter by user with no center
     */
    @GetMapping("/getUser")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> getUser(@RequestParam(required = false) String userName,
                                      @RequestParam(required = false) String fullName,
                                      @RequestParam(required = false) String codingLanguage,
                                      @RequestParam(required = false) String email,
                                      @RequestParam(required = false, defaultValue = "1") String type,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size){

        List<UserInforDto> users;
        List<UserInforNoCenterDTO> usersNoCenter;
        Pageable paging = PageRequest.of(page, size);
        Page<UserInforDto> usersPage = null;
        Page<UserInforNoCenterDTO> usersPageNoCenter = null;

        if(type.equals("1")) {

            usersPage = userServiceImpl.getUser(userName, fullName, codingLanguage, email, paging);
        }
        if(type.equals("2")){

            usersPageNoCenter = userServiceImpl.getUserNoCenter(userName, fullName, codingLanguage, email, paging);
            usersNoCenter = usersPageNoCenter.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("users", usersNoCenter);
            response.put("currentPage", usersPageNoCenter.getNumber());
            response.put("totalItems", usersPageNoCenter.getTotalElements());
            response.put("totalPages", usersPageNoCenter.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        usersPage = userServiceImpl.getUser(userName, fullName, codingLanguage, email, paging);

        users = usersPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("currentPage", usersPage.getNumber());
        response.put("totalItems", usersPage.getTotalElements());
        response.put("totalPages", usersPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/createUser")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signUpRequest, UserGrade userGrade) {

        return ResponseEntity.ok(userServiceImpl.createUser(signUpRequest, userGrade));
    }


    @PutMapping("/editUser/{userId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> editUser(@PathVariable("userId") Long userId, @RequestBody Users users){

        userServiceImpl.editUser(userId, users);
        return ResponseEntity.ok("User edited");
    }


    @DeleteMapping("/deleteUser/{userId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId){

        userServiceImpl.deleteUser(userId);
        return ResponseEntity.ok("User deleted");
    }


    @PutMapping("/tranferUser/{userId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> tranferUser(@PathVariable("userId") Long userId, @RequestBody Users users){

        userServiceImpl.tranferUser(userId, users);
        return ResponseEntity.ok("User tranfered");
    }
}
