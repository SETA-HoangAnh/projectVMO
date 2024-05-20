package com.example.project.service.Impl;

import com.example.project.dto.ProjectDto;
import com.example.project.entity.Project;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.ProjectRepository;
import com.example.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

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

    @Override
    public void addProject(Project project){

        projectRepository.save(project);
    }


    @Override
    public void editProject(Long projectId, Project project){

        Project projectFind = projectRepository.findById(projectId)
                .orElseThrow(()-> new ResourceNotFoundException("Project", "id", projectId));
        projectFind.setProjectName(project.getProjectName());
        projectFind.setProjectCode(project.getProjectCode());
        projectFind.setStartDate(project.getStartDate());
        projectFind.setEndDate(project.getEndDate());
        projectFind.setCodingLanguage(project.getCodingLanguage());
        projectFind.setProjectStatus(project.getProjectStatus());

        projectRepository.save(projectFind);
    }


//    @Override
//    public void deleteProject(Long projectId){
//
//        Project projectFind = projectRepository.findById(projectId)
//                .orElseThrow(()-> new ResourceNotFoundException("Project", "id", projectId));
//
//        projectRepository.deleteById(projectFind.getProjectId());
//    }
}
