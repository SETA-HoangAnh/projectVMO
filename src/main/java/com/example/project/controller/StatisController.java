package com.example.project.controller;

import com.example.project.dto.StatisByCenterDto;
import com.example.project.dto.UserInforNoCenterDTO;
import com.example.project.service.Impl.StatisServiceImpl;

import com.example.project.service.StatisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/statistical")
@RequiredArgsConstructor
public class StatisController {

    private final StatisServiceImpl statisServiceImpl;

    private final StatisService statisService;


    /**
     * API thống kê fresher theo trung tâm
     */
    @GetMapping("/getFresherByCenter")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<List<StatisByCenterDto>> getFresherByCenter(@RequestParam(required = false) Long centerId){

        List<StatisByCenterDto> statis = statisService.getByCenter(centerId);
        return new ResponseEntity<>(statis, HttpStatus.OK);
    }

    //Chua lam xong api nay
//
//    @GetMapping("/getFresherByScore")
////    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
//    public ResponseEntity<?> getFresherByScore(){
//
//        return ResponseEntity.ok(statisServiceImpl.listByAverage());
//    }

    /**
     * API danh sách điểm trung bình
     */
    @GetMapping("/scoreList")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public List<Long> scoreList(){

        return statisServiceImpl.scoreList();
    }


    /**
     * API thống kê fresher theo điểm trung bình
     */
    @GetMapping("/listInforByAverage/{averageScore}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public List<UserInforNoCenterDTO> listInforByAverage(@PathVariable("averageScore") Long averageScore){

        return statisService.listInforByAverage(averageScore);
    }

}
