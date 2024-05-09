package com.example.project.controller;

import com.example.project.dto.UserInforDto;
import com.example.project.dto.UserInforNoCenterDTO;
import com.example.project.entity.Center;
import com.example.project.service.CenterServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${apiPrefix}/center")
public class CenterController {

    private final CenterServiceImpl centerServiceImpl;

    public CenterController(CenterServiceImpl centerServiceImpl) {
        this.centerServiceImpl = centerServiceImpl;
    }

    @GetMapping("/getCenter")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> getCenter(@RequestParam(required = false) String centerName,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size){

        List<Center> centers;
        Pageable paging = PageRequest.of(page, size);
        Page<Center> centersPage = null;

        centersPage = centerServiceImpl.getCenter(centerName, paging);
        centers = centersPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("centers", centers);
        response.put("currentPage", centersPage.getNumber());
        response.put("totalItems", centersPage.getTotalElements());
        response.put("totalPages", centersPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
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
