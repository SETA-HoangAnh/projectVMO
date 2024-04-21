package com.example.project.controller;

import com.example.project.dto.StatisByAverageDto;
import com.example.project.dto.UserInforDto;
import com.example.project.dto.UserInforNoCenterDTO;
import com.example.project.service.StatisServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/statistical")
public class StatisController {

    private final StatisServiceImpl statisServiceImpl;

    public StatisController(StatisServiceImpl statisServiceImpl) {
        this.statisServiceImpl = statisServiceImpl;
    }

    @GetMapping("/getFresherByCenter")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> getFresherByCenter(@RequestParam(required = false) Long centerId){

        return ResponseEntity.ok(statisServiceImpl.getByCenter(centerId));
    }

    //Chua lam xong api nay
//
//    @GetMapping("/getFresherByScore")
////    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
//    public ResponseEntity<?> getFresherByScore(){
//
//        return ResponseEntity.ok(statisServiceImpl.listByAverage());
//    }

    @GetMapping("/scoreList")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public List<Long> scoreList(){

        return statisServiceImpl.scoreList();
    }

    @GetMapping("/listInforByAverage/{averageScore}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public List<UserInforNoCenterDTO> listInforByAverage(@PathVariable("averageScore") Long averageScore){

        return statisServiceImpl.listInforByAverage(averageScore);
    }

}
