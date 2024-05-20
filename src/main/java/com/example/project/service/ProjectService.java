package com.example.project.service;

import com.example.project.dto.ProjectDto;
import com.example.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    Page<ProjectDto> getProject(String projectName, String projectCode, Pageable pageable);

    void addProject(Project project);

    void editProject(Long projectId, Project project);

//    void deleteProject(Long projectId);


}
