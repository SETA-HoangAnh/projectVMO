package com.example.project.controller;

import com.example.project.entity.Center;
import com.example.project.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${apiPrefix}/centers")
public class CenterControllers {
    @Autowired
    private CenterService centerService;

    @GetMapping("/")
    public ResponseEntity<Page<Center>> getCenter(Pageable pageable) {
        Page<Center> centersPage = centerService.getAllCenter(pageable);
        return new ResponseEntity<>(centersPage, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Center>> searchCentersByName(@RequestParam String name, Pageable pageable) {
        Page<Center> centersPage = centerService.getAllCenter(pageable);
        return new ResponseEntity<>(centersPage, HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<String> addCenter(@RequestBody Center center) {
        centerService.save(center);
        return ResponseEntity.ok("Center added");
    }


    @PutMapping("/{centerId}")
    public ResponseEntity<String> updateCenter(@PathVariable("centerId") Long centerId, @RequestBody Center center) {
        centerService.save(center);
        return ResponseEntity.ok("Center edited");
    }


    @DeleteMapping("/{centerId}")
    public ResponseEntity<String> deleteCenter(@PathVariable("centerId") Long centerId) {
        centerService.delete(centerId);
        return ResponseEntity.ok("Center deleted");
    }
}
