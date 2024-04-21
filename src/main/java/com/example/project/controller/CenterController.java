package com.example.project.controller;

import com.example.project.entity.Center;
import com.example.project.service.CenterServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${apiPrefix}/center")
public class CenterController {

    private final CenterServiceImpl centerServiceImpl;

    public CenterController(CenterServiceImpl centerServiceImpl) {
        this.centerServiceImpl = centerServiceImpl;
    }

    @GetMapping("/getCenter")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> getCenter(@RequestParam(required = false) String centerName){

        return ResponseEntity.ok(centerServiceImpl.getCenter(centerName));
    }


    @PostMapping("/addCenter")
    @PreAuthorize("@userServiceImpl.getRoles().contains('ROLE_MANAGER') ")
    public ResponseEntity<?> addCenter(@RequestBody Center center){

        centerServiceImpl.addCenter(center);
        return ResponseEntity.ok("Center added");
    }


    @PutMapping("/editCenter/{centerId}")
    @PreAuthorize("@userServiceImpl.getRoles().contains('ROLE_MANAGER') ")
    public ResponseEntity<?> editCenter(@PathVariable("centerId") Long centerId, @RequestBody Center center){

        centerServiceImpl.editCenter(centerId, center);
        return ResponseEntity.ok("Center edited");
    }


    @DeleteMapping("/deleteCenter/{centerId}")
    @PreAuthorize("@userServiceImpl.getRoles().contains('ROLE_MANAGER') ")
    public ResponseEntity<?> deleteCenter(@PathVariable("centerId") Long centerId){

        centerServiceImpl.deleteCenter(centerId);
        return ResponseEntity.ok("Center deleted");
    }
}
