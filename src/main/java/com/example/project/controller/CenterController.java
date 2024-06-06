package com.example.project.controller;

import com.example.project.entity.Center;
import com.example.project.service.CenterService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${apiPrefix}/center")
public class CenterController {

    private final CenterService centerService;

    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }


    /**
     * API danh sách trung tâm
     */
    @GetMapping("/")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<Page<Center>> getCenter(@RequestParam(required = false) String centerName,
                                       Pageable pageable){

        Page<Center> centersPage = centerService.getAllCenter(centerName, pageable);
        return new ResponseEntity<>(centersPage, HttpStatus.OK);
    }


    /**
     * API thêm trung tâm
     */
    @PostMapping("/")
    @PreAuthorize("@userServiceImpl.getRoles().contains('ROLE_MANAGER') ")
    public ResponseEntity<String> addCenter(@Valid @RequestBody Center center) {

        centerService.save(center);
        return ResponseEntity.ok("Center added");
    }


    /**
     * API sửa trung tâm
     */
    @PutMapping("/{centerId}")
    @PreAuthorize("@userServiceImpl.getRoles().contains('ROLE_MANAGER') ")
    public ResponseEntity<String> updateCenter(@PathVariable("centerId") Long centerId, @Valid @RequestBody Center center) {

        centerService.update(centerId, center);
        return ResponseEntity.ok("Center edited");
    }


    /**
     * API xóa trung tâm
     */
    @DeleteMapping("/{centerId}")
    @PreAuthorize("@userServiceImpl.getRoles().contains('ROLE_MANAGER') ")
    public ResponseEntity<String> deleteCenter(@PathVariable("centerId") Long centerId) {

        return centerService.delete(centerId);
    }
}
