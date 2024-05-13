package com.example.project.controller;

import com.example.project.dto.UserGradeDto;
import com.example.project.entity.UserGrade;
import com.example.project.service.Impl.UserGradeServiceImpl;

import com.example.project.service.UserGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${apiPrefix}/userGrade")
@RequiredArgsConstructor
public class UserGradeController {

    private final UserGradeService userGradeService;

    /**
     * API danh sách điểm theo user
     */
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserGradeDto> getGrade(@PathVariable("userId") Long userId){

        UserGradeDto userGrade = userGradeService.getGrade(userId);
        return new ResponseEntity<>(userGrade, HttpStatus.OK);
    }

    /**
     * API sửa, thêm điểm user
     */
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    @PutMapping("/{userId}")
    public ResponseEntity<String> editGrade(@PathVariable("userId") Long userId,
                                       @RequestBody UserGrade userGrade){

        userGradeService.editGrade(userId, userGrade);
        return ResponseEntity.ok("Grade edited");
    }

}
