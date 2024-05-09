package com.example.project.controller;

import com.example.project.dto.ProjectDto;
import com.example.project.entity.Center;
import com.example.project.entity.Project;
import com.example.project.service.ProjectServiceImpl;

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
@RequestMapping("${apiPrefix}/project")
public class ProjectController {

    private final ProjectServiceImpl projectServiceImpl;

    public ProjectController(ProjectServiceImpl projectServiceImpl) {
        this.projectServiceImpl = projectServiceImpl;
    }

    @GetMapping("/getProject")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<?> getProject(@RequestParam(required = false) String projectName,
                                        @RequestParam(required = false) String projectCode,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size){

        List<ProjectDto> projects;
        Pageable paging = PageRequest.of(page, size);
        Page<ProjectDto> projectsPage = null;

        projectsPage = projectServiceImpl.getProject(projectName, projectCode, paging);
        projects = projectsPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("projects", projects);
        response.put("currentPage", projectsPage.getNumber());
        response.put("totalItems", projectsPage.getTotalElements());
        response.put("totalPages", projectsPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
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
