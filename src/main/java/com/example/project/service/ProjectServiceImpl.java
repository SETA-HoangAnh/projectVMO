package com.example.project.service;

import com.example.project.dto.ProjectDto;
import com.example.project.entity.Project;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.ProjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Page<ProjectDto> getProject(String projectName, String projectCode, Pageable pageable){

        if(projectName == null){
            projectName = "";
        }
        if(projectCode == null){
            projectCode = "";
        }

        Page<ProjectDto> projectDtoList = projectRepository.getProject(projectName, projectCode, pageable);
        return projectDtoList;
    }


    public Project addProject(Project project){

        return projectRepository.save(project);
    }


    public Project editProject(Long projectId, Project project){

        Project projectFind = projectRepository.findById(projectId)
                .orElseThrow(()-> new ResourceNotFoundException("Project", "id", projectId));
        projectFind.setProjectName(project.getProjectName());
        projectFind.setProjectCode(project.getProjectCode());
        projectFind.setStartDate(project.getStartDate());
        projectFind.setEndDate(project.getEndDate());
        projectFind.setCodingLanguage(project.getCodingLanguage());
        projectFind.setProjectStatus(project.getProjectStatus());

        return projectRepository.save(projectFind);
    }


    public Project deleteProject(Long projectId){

        Project projectFind = projectRepository.findById(projectId)
                .orElseThrow(()-> new ResourceNotFoundException("Project", "id", projectId));

        projectRepository.deleteById(projectFind.getProjectId());

        return null;
    }
}
