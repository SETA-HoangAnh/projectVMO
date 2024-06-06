package com.example.project.controller;

import com.example.project.dto.ProjectDto;
import com.example.project.entity.Project;
import com.example.project.service.Impl.ProjectServiceImpl;

import com.example.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("${apiPrefix}/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * API danh sách dự án
     */
    @GetMapping("/")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<Page<ProjectDto>> getProject(@RequestParam(required = false) String projectName,
                                        @RequestParam(required = false) String projectCode,
                                        Pageable pageable){

        Page<ProjectDto> project = projectService.getProject(projectName, projectCode, pageable);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }


    /**
     * API thêm dự án
     */
    @PostMapping("/")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<String> addProject(@Valid @RequestBody Project project){

        projectService.addProject(project);
        return ResponseEntity.ok("Project saved");
    }


    /**
     * API sửa dự án
     */
    @PutMapping("/{projectId}")
    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
    public ResponseEntity<String> editProject(@PathVariable("projectId") Long projectId,
                                              @Valid @RequestBody Project project){

        projectService.editProject(projectId, project);
        return ResponseEntity.ok("Project edited");
    }


//    /**
//     * API xóa dự án
//     */
//    @DeleteMapping("/{projectId}")
//    @PreAuthorize( "@userServiceImpl.getRoles().contains('ROLE_MANAGER')")
//    public ResponseEntity<String> deleteProject(@PathVariable("projectId") Long projectId){
//
//        projectService.deleteProject(projectId);
//        return ResponseEntity.ok("Project deleted");
//    }

}
