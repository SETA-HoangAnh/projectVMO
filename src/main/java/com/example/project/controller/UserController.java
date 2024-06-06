package com.example.project.controller;

import com.example.project.dto.UserInforDto;
import com.example.project.dto.UserInforNoCenterDTO;
import com.example.project.entity.UserGrade;
import com.example.project.entity.Users;
import com.example.project.payload.SignupRequest;
import com.example.project.security.jwt.AuthEntryPointJwt;
import com.example.project.service.Impl.UserServiceImpl;

import com.example.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${apiPrefix}/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class UserController{

    private final UserService userService;


    /**
     * API danh sách fresher
     */

    /**
     * type = 1: filter by user with center
     * type = 2: filter by user with no center
     */
    @GetMapping("/")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> getUser(@RequestParam(required = false) String userName,
                                      @RequestParam(required = false) String fullName,
                                      @RequestParam(required = false) String codingLanguage,
                                      @RequestParam(required = false) String email,
                                      @RequestParam(required = false, defaultValue = "1") String type,
                                      Pageable pageable){

        Page<UserInforDto> usersNoCenter = null;
        Page<UserInforNoCenterDTO> usersPageNoCenter = null;

        if(type.equals("1")) {

            usersNoCenter = userService.getUser(userName, fullName, codingLanguage, email, pageable);
            return new ResponseEntity<>(usersNoCenter, HttpStatus.OK);
        }
        if(type.equals("2")){

            usersPageNoCenter = userService.getUserNoCenter(userName, fullName, codingLanguage, email, pageable);
            return new ResponseEntity<>(usersPageNoCenter, HttpStatus.OK);
        }

        usersNoCenter = userService.getUser(userName, fullName, codingLanguage, email, pageable);

        return new ResponseEntity<>(usersNoCenter, HttpStatus.OK);
    }


    /**
     * API thêm user
     */
    @PostMapping("/")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signUpRequest, UserGrade userGrade) {

        return userService.createUser(signUpRequest, userGrade);
    }


    /**
     * API sửa user
     */
    @PutMapping("/{userId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<String> editUser(@PathVariable("userId") Long userId, @Valid @RequestBody Users users){

        userService.editUser(userId, users);
        return ResponseEntity.ok("User edited");
    }


    /**
     * API xóa user
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId){

        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted");
    }

    /**
     * API chuyển fresher vào trung tâm
     */
    @PutMapping("/tranferUser/{userId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<String> tranferUser(@PathVariable("userId") Long userId, @RequestBody Users users){

        userService.tranferUser(userId, users);
        return ResponseEntity.ok("User tranfered");
    }

    /**
     * API xóa fresher khỏi trung tâm
     */
    @PutMapping("/removeFromCenter")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<String> removeFromCenter(@RequestParam Long userId,
                                                   @RequestParam Long centerId){

        userService.removeFromCenter(userId,centerId);
        return ResponseEntity.ok("Deleted from center");
    }
}
