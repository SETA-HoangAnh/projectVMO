package com.example.project.controller;

import com.example.project.service.StatisServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

        return statisServiceImpl.getByCenter(centerId);
    }

    //Chua lam xong api nay

//    @GetMapping("/getFresherByScore")
////    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
//    public ResponseEntity<?> getFresherByScore(){
//
//        return statisServiceImpl.scoreDtoList();
//    }

}
