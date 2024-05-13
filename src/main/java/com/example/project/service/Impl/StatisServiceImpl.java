package com.example.project.service.Impl;

import com.example.project.dto.*;
import com.example.project.entity.UserGrade;
import com.example.project.repository.CenterRepository;
import com.example.project.repository.UserGradeRepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.StatisService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisServiceImpl implements StatisService {

    private static UserRepository userRepository;

    private final CenterRepository centerRepository;

    private static UserGradeRepository userGradeRepository;

    public StatisServiceImpl(UserRepository userRepository, CenterRepository centerRepository, UserGradeRepository userGradeRepository) {
        this.userRepository = userRepository;
        this.centerRepository = centerRepository;
        this.userGradeRepository = userGradeRepository;
    }

    @Override
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


    public List<StatisByAverageDto> listByAverage(){

         return null;
    }


    public static List<Long> scoreList(){

        List<Long> listScore = userGradeRepository.scoreList();
        return listScore;
    }


    @Override
    public List<UserInforNoCenterDTO> listInforByAverage(Long averageScore){

        List<UserInforNoCenterDTO> list = userGradeRepository.listInforByAverage(averageScore);
        return list;
    }


    public static List<Long> scoreList2(){

        List<Long> listScore = userGradeRepository.scoreList();
        return listScore;
    }


    public static List<UserInforNoCenterDTO> listInforByAverage2(Long averageScore){

        List<UserInforNoCenterDTO> list = userGradeRepository.listInforByAverage(averageScore);
        return list;
    }
}
