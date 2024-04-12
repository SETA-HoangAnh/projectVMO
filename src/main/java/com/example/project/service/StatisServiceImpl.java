package com.example.project.service;

import com.example.project.dto.StatisByCenterDto;
import com.example.project.dto.UserDetailDto;
import com.example.project.entity.Center;
import com.example.project.repository.CenterRepository;
import com.example.project.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisServiceImpl {

    private static UserRepository userRepository;

    private final CenterRepository centerRepository;

    public StatisServiceImpl(UserRepository userRepository, CenterRepository centerRepository) {
        this.userRepository = userRepository;
        this.centerRepository = centerRepository;
    }


    public ResponseEntity<?> getByCenter(Long centerId){

       List<StatisByCenterDto> centerFind = centerRepository.getByCenter(centerId);
       return ResponseEntity.ok(centerFind);
    }


    public static Long countFresher(Long centerId){

        Long count = userRepository.countFresher(centerId);
        return count;
    }

    public static List<UserDetailDto> userDetailDtoList(Long centerId){

        List<UserDetailDto> list = userRepository.getUserDetail(centerId);
        return list;
    }
}
