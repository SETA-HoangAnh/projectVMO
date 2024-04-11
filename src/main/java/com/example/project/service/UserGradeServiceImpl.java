package com.example.project.service;

import com.example.project.dto.UserGradeDto;
import com.example.project.entity.UserGrade;
import com.example.project.entity.Users;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.UserGradeRepository;
import com.example.project.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserGradeServiceImpl {

    private static UserGradeRepository userGradeRepository;

    private static UserRepository userRepository;

    public UserGradeServiceImpl(UserGradeRepository userGradeRepository, UserRepository userRepository) {
        UserGradeServiceImpl.userGradeRepository = userGradeRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getGrade(Long userId){

        return ResponseEntity.ok(userGradeRepository.getGrade(userId));
    }

    public ResponseEntity<?> editGrade(Long userId, UserGrade userGrade){

        Users userFind = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        UserGradeDto userGradeDto = userGradeRepository.getGrade(userFind.getUserId());
        UserGrade gradeFind = userGradeRepository.findById(userGradeDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        gradeFind.setExercise1(userGrade.getExercise1());
        gradeFind.setExercise2(userGrade.getExercise2());
        gradeFind.setExercise3(userGrade.getExercise3());
        userGradeRepository.save(gradeFind);

        return ResponseEntity.ok("Grade edited");
    }

    public static Double sumScore(Long userId){

        Users userFind = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Long getGradeId = userGradeRepository.getGrade2(userFind.getUserId());
        UserGrade gradeFind = userGradeRepository.findGradebyId(getGradeId);
        Double ex1 = gradeFind.getExercise1();
        Double ex2 = gradeFind.getExercise2();
        Double ex3 = gradeFind.getExercise3();
        Double sum = (ex1 + ex2 + ex3)/3;
        return sum;

    }

}
