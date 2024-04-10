package com.example.project.controller;

import com.example.project.service.UserGradeServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${apiPrefix}/user_grade")
public class UserGradeController {

    private final UserGradeServiceImpl userGradeServiceImpl;

    public UserGradeController(UserGradeServiceImpl userGradeServiceImpl) {
        this.userGradeServiceImpl = userGradeServiceImpl;
    }

//    @GetMapping("/getUserGrade/{userId}")
}
