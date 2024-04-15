package com.example.project.service;

import com.example.project.dto.ProjectDto;
import com.example.project.entity.Project;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ResponseEntity<?> getProject(String projectName, String projectCode){

        if(projectName == null){
            projectName = "";
        }
        if(projectCode == null){
            projectCode = "";
        }

        List<ProjectDto> projectDtoList = projectRepository.getProject(projectName, projectCode);

        return ResponseEntity.ok(projectDtoList);
    }


    public ResponseEntity<?> addProject(Project project){

        projectRepository.save(project);
        return ResponseEntity.ok("Project saved");
    }


    public ResponseEntity<?> editProject(Long projectId, Project project){

        Project projectFind = projectRepository.findById(projectId)
                .orElseThrow(()-> new ResourceNotFoundException("Project", "id", projectId));
        projectFind.setProjectName(project.getProjectName());
        projectFind.setProjectCode(project.getProjectCode());
        projectFind.setStartDate(project.getStartDate());
        projectFind.setEndDate(project.getEndDate());
        projectFind.setCodingLanguage(project.getCodingLanguage());
        projectFind.setProjectStatus(project.getProjectStatus());

        projectRepository.save(projectFind);

        return ResponseEntity.ok("Project edited");
    }


    public ResponseEntity<?> deleteProject(Long projectId){

        Project projectFind = projectRepository.findById(projectId)
                .orElseThrow(()-> new ResourceNotFoundException("Project", "id", projectId));

        projectRepository.deleteById(projectFind.getProjectId());

        return ResponseEntity.ok("Project deleted");
    }
}
