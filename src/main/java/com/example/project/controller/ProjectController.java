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

        return ResponseEntity.ok(projectServiceImpl.getProject(projectName, projectCode));
    }


    @PostMapping("/addProject")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> addProject(@RequestBody Project project){

        projectServiceImpl.addProject(project);
        return ResponseEntity.ok("Project saved");
    }


    @PutMapping("/editProject/{projectId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> editProject(@PathVariable("projectId") Long projectId,
                                         @RequestBody Project project){

        projectServiceImpl.editProject(projectId, project);
        return ResponseEntity.ok("Project edited");
    }


    @DeleteMapping("/deleteProject/{projectId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> deleteProject(@PathVariable("projectId") Long projectId){

        projectServiceImpl.deleteProject(projectId);
        return ResponseEntity.ok("Project deleted");
    }

}
