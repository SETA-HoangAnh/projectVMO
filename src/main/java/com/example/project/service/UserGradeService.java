package com.example.project.service;

import com.example.project.dto.UserGradeDto;
import com.example.project.entity.UserGrade;

public interface UserGradeService {

    UserGradeDto getGrade(Long userId);

    void editGrade(Long userId, UserGrade userGrade);
}
