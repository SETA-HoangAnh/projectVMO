package com.example.project.controller;

import com.example.project.entity.Center;
import com.example.project.service.CenterServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/center")
public class CenterController {

    private final CenterServiceImpl centerServiceImpl;

    public CenterController(CenterServiceImpl centerServiceImpl) {
        this.centerServiceImpl = centerServiceImpl;
    }

    @GetMapping("/getCenter")
    public ResponseEntity<?> getCenter(@RequestParam(required = false) String centerName){

        return centerServiceImpl.getCenter(centerName);
    }


    @PostMapping("/addCenter")
    public ResponseEntity<?> addCenter(@RequestBody Center center){

        return centerServiceImpl.addCenter(center);
    }


    @PutMapping("/editCenter/{centerId}")
    public ResponseEntity<?> editCenter(@PathVariable("centerId") Long centerId, @RequestBody Center center){

        return centerServiceImpl.editCenter(centerId, center);
    }


    @DeleteMapping("/deleteCenter/{centerId}")
    public ResponseEntity<?> deleteCenter(@PathVariable("centerId") Long centerId){

        return centerServiceImpl.deleteCenter(centerId);
    }
}
