package com.example.project.controller;

import com.example.project.entity.UserGrade;
import com.example.project.service.UserGradeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${apiPrefix}/userGrade")
public class UserGradeController {

    private final UserGradeServiceImpl userGradeServiceImpl;

    public UserGradeController(UserGradeServiceImpl userGradeServiceImpl) {
        this.userGradeServiceImpl = userGradeServiceImpl;
    }

    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    @GetMapping("/getGrade/{userId}")
    public ResponseEntity<?> getGrade(@PathVariable("userId") Long userId){

        return userGradeServiceImpl.getGrade(userId);
    }

    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    @PutMapping("/editGrade/{userId}")
    public ResponseEntity<?> editGrade(@PathVariable("userId") Long userId,
                                       @RequestBody UserGrade userGrade){

        return userGradeServiceImpl.editGrade(userId, userGrade);
    }

}
