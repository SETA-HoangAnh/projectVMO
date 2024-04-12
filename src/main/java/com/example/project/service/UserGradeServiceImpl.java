package com.example.project.service;

import com.example.project.dto.UserGradeDto;
import com.example.project.dto.UserGradeNoSumDto;
import com.example.project.entity.UserGrade;
import com.example.project.entity.Users;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.UserGradeRepository;
import com.example.project.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

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
        UserGradeNoSumDto userGradeNoSumDto = userGradeRepository.getGradeNoSum(userFind.getUserId());
        UserGrade gradeFind = userGradeRepository.findById(userGradeNoSumDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        gradeFind.setExercise1(userGrade.getExercise1());
        gradeFind.setExercise2(userGrade.getExercise2());
        gradeFind.setExercise3(userGrade.getExercise3());
        userGradeRepository.save(gradeFind);

        return ResponseEntity.ok("Grade edited");
    }

    public static Long sumScore(Long userId){

        Users userFind = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Long getGradeId = userGradeRepository.getGrade2(userFind.getUserId());
        UserGrade gradeFind = userGradeRepository.findGradebyId(getGradeId);
        Long ex1 = gradeFind.getExercise1();
        Long ex2 = gradeFind.getExercise2();
        Long ex3 = gradeFind.getExercise3();
        Long sum = (ex1 + ex2 + ex3)/3;
//        DecimalFormat decimalFormat = new DecimalFormat("#");
//        String result = decimalFormat.format(sum);
//        return result;
        return sum;

    }

}
