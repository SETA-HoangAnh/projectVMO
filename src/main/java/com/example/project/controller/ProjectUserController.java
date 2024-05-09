package com.example.project.controller;

import com.example.project.dto.ProjectAndUserDto;
import com.example.project.dto.ProjectDto;
import com.example.project.entity.ProjectUser;
import com.example.project.service.ProjectUserServiceImpl;

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
public class ProjectUserController {

    private final ProjectUserServiceImpl projectUserServiceImpl;

    public ProjectUserController(ProjectUserServiceImpl projectUserServiceImpl) {
        this.projectUserServiceImpl = projectUserServiceImpl;
    }


    @GetMapping("/listProjectAndUser/{projectId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> listProjectUser(@PathVariable("projectId") Long projectId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size){

        List<ProjectAndUserDto> projectAndUsers;
        Pageable paging = PageRequest.of(page, size);
        Page<ProjectAndUserDto> projectAndUsersPage = null;

        projectAndUsersPage = projectUserServiceImpl.listProjectUser(projectId, paging);
        projectAndUsers = projectAndUsersPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("projectAndUsers", projectAndUsers);
        response.put("currentPage", projectAndUsersPage.getNumber());
        response.put("totalItems", projectAndUsersPage.getTotalElements());
        response.put("totalPages", projectAndUsersPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/listProjectByUser")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_FRESHER')")
    public ResponseEntity<?> listProjectByUser(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size){

        List<ProjectDto> projectList;
        Pageable paging = PageRequest.of(page, size);
        Page<ProjectDto> projectListPage = null;

        projectListPage = projectUserServiceImpl.listProjectByUser(paging);
        projectList = projectListPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("projectList", projectList);
        response.put("currentPage", projectListPage.getNumber());
        response.put("totalItems", projectListPage.getTotalElements());
        response.put("totalPages", projectListPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @PostMapping("/addToProject")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> addToProject(@RequestBody List<ProjectUser> projectUserList, ProjectUser projectUser){

        projectUserServiceImpl.addToProject(projectUserList);
        return ResponseEntity.ok("Added to project");
    }


    @DeleteMapping("/removeFromProject")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> removeFromProject(@RequestParam Long projectId,
                                               @RequestParam Long userId){

        projectUserServiceImpl.removeFromProject(projectId, userId);
        return ResponseEntity.ok("Removed from project");
    }
}
