package com.example.project.controller;

import com.example.project.entity.Project;
import com.example.project.service.ProjectServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${apiPrefix}/project")
public class ProjectController {

    private final ProjectServiceImpl projectServiceImpl;

    public ProjectController(ProjectServiceImpl projectServiceImpl) {
        this.projectServiceImpl = projectServiceImpl;
    }

    @GetMapping("/getProject")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> getProject(@RequestParam(required = false) String projectName,
                                        @RequestParam(required = false) String projectCode){

        return projectServiceImpl.getProject(projectName, projectCode);
    }


    @PostMapping("/addProject")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> addProject(@RequestBody Project project){

        return projectServiceImpl.addProject(project);
    }


    @PutMapping("/editProject/{projectId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> editProject(@PathVariable("projectId") Long projectId,
                                         @RequestBody Project project){

        return projectServiceImpl.editProject(projectId, project);
    }


    @DeleteMapping("/deleteProject/{projectId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") Long projectId){

        return projectServiceImpl.deleteProject(projectId);
    }

}
