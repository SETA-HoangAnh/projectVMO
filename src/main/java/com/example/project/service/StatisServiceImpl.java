package com.example.project.service;

import com.example.project.dto.*;
import com.example.project.entity.UserGrade;
import com.example.project.repository.CenterRepository;
import com.example.project.repository.UserGradeRepository;
import com.example.project.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisServiceImpl {

    private static UserRepository userRepository;

    private final CenterRepository centerRepository;

    private static UserGradeRepository userGradeRepository;

    public StatisServiceImpl(UserRepository userRepository, CenterRepository centerRepository, UserGradeRepository userGradeRepository) {
        this.userRepository = userRepository;
        this.centerRepository = centerRepository;
        this.userGradeRepository = userGradeRepository;
    }


    public List<StatisByCenterDto> getByCenter(Long centerId){

       List<StatisByCenterDto> centerFind = centerRepository.getByCenter(centerId);
       return centerFind;
    }


    public static Long countFresher(Long centerId){

        Long count = userRepository.countFresher(centerId);
        return count;
    }

    public static List<UserDetailDto> userDetailDtoList(Long centerId){

        List<UserDetailDto> list = userRepository.getUserDetail(centerId);
        return list;
    }


    public static List<ScoreListDetailDto> scoreList(){

        List<ScoreListDetailDto> listScore = userGradeRepository.scoreList();
        return listScore;
    }

    public ResponseEntity<?> scoreDtoList(){

        List<StatisByScoreDto> listScoreDto = userGradeRepository.scoreDtoList();
//        List<Long> newList = new ArrayList<>();
//        DecimalFormat decimalFormat = new DecimalFormat("#");
//        for(StatisByScoreDto i : listScoreDto){
//            String result = decimalFormat.format(i);
//            Long formatResult = Long.parseLong(result);
//            newList.add(formatResult);
//        }
//        return ResponseEntity.ok(newList);
        return ResponseEntity.ok(listScoreDto);
    }
}
