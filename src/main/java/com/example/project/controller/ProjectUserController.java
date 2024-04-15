package com.example.project.controller;

import com.example.project.entity.ProjectUser;
import com.example.project.service.ProjectUserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/projectUser")
public class ProjectUserController {

    private final ProjectUserServiceImpl projectUserServiceImpl;

    public ProjectUserController(ProjectUserServiceImpl projectUserServiceImpl) {
        this.projectUserServiceImpl = projectUserServiceImpl;
    }


    @GetMapping("/listProjectAndUser/{projectId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> listProjectUser(@PathVariable("projectId") Long projectId){

        return projectUserServiceImpl.listProjectUser(projectId);
    }


    @GetMapping("/listProjectByUser")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_FRESHER')")
    public ResponseEntity<?> listProjectByUser(){

        return projectUserServiceImpl.listProjectByUser();
    }


    @PostMapping("/addToProject")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> addToProject(@RequestBody List<ProjectUser> projectUserList){

        return projectUserServiceImpl.addToProject(projectUserList);
    }


    @DeleteMapping("/removeFromProject")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> removeFromProject(@RequestParam Long projectId,
                                               @RequestParam Long userId){

        return projectUserServiceImpl.removeFromProject(projectId, userId);
    }
}
