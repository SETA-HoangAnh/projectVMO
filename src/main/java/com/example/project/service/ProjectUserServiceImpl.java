package com.example.project.service;

import com.example.project.dto.ProjectAndUserDto;
import com.example.project.dto.ProjectDto;
import com.example.project.entity.ProjectUser;
import com.example.project.repository.ProjectUserRepository;
import com.example.project.security.service.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectUserServiceImpl {

    private final ProjectUserRepository projectUserRepository;

    public ProjectUserServiceImpl(ProjectUserRepository projectUserRepository) {
        this.projectUserRepository = projectUserRepository;
    }

    public ResponseEntity<?> listProjectUser(Long projectId){

        List<ProjectAndUserDto> projectAndUserDtoList = projectUserRepository.listProjectAndUser(projectId);
        return ResponseEntity.ok(projectAndUserDtoList);
    }

    public ResponseEntity<?> listProjectByUser(){

        List<ProjectDto> listProjectByUser = projectUserRepository.listProjectByUser(getUserLoginId());
        return ResponseEntity.ok(listProjectByUser);
    }


    public ResponseEntity<?> addToProject(List<ProjectUser> projectUserList){

        projectUserRepository.saveAll(projectUserList);
        return ResponseEntity.ok("Added to project");
    }


    public ResponseEntity<?> removeFromProject(Long projectId, Long userId){

        ProjectUser projectUserFind = projectUserRepository.findProjectByUser(projectId, userId);
        projectUserRepository.deleteById(projectUserFind.getProjectUserId());
        return ResponseEntity.ok("Removed from project");
    }

    public static Long getUserLoginId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getId();
    }
}
