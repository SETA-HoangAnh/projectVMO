package com.example.project.controller;

import com.example.project.dto.ProjectAndUserDto;
import com.example.project.dto.ProjectDto;
import com.example.project.entity.ProjectUser;
import com.example.project.service.Impl.ProjectUserServiceImpl;

import com.example.project.service.ProjectUserService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("${apiPrefix}/projectUser")
@RequiredArgsConstructor
public class ProjectUserController {

    private final ProjectUserService projectUserService;

    /**
     * API danh sách user theo project
     */
    @GetMapping("/listProjectAndUser/{projectId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<Page<ProjectAndUserDto>> listProjectUser(@PathVariable("projectId") Long projectId,
                                             Pageable pageable){

        Page<ProjectAndUserDto> projectUser = projectUserService.listProjectUser(projectId, pageable);
        return new ResponseEntity<>(projectUser, HttpStatus.OK);
    }


    /**
     * API danh sách project theo user
     */
    @GetMapping("/listProjectByUser")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_FRESHER')")
    public ResponseEntity<Page<ProjectDto>> listProjectByUser(Pageable pageable){

        Page<ProjectDto> project = projectUserService.listProjectByUser(pageable);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }


    /**
     * API thêm user vào project
     */
    @PostMapping("/addToProject")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> addToProject(@RequestBody List<ProjectUser> projectUserList, ProjectUser projectUser){

        projectUserService.addToProject(projectUserList);
        return ResponseEntity.ok("Added to project");
    }


    /**
     * API xóa user khỏi project
     */
    @DeleteMapping("/removeFromProject")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> removeFromProject(@RequestParam Long projectId,
                                               @RequestParam Long userId){

        projectUserService.removeFromProject(projectId, userId);
        return ResponseEntity.ok("Removed from project");
    }
}
