package com.example.project.service;

import com.example.project.dto.ProjectAndUserDto;
import com.example.project.dto.ProjectDto;
import com.example.project.entity.ProjectUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectUserService {

    Page<ProjectAndUserDto> listProjectUser(Long projectId, Pageable pageable);

    Page<ProjectDto> listProjectByUser(Pageable pageable);

    List<ProjectUser> addToProject(List<ProjectUser> projectUserList);

    void removeFromProject(Long projectId, Long userId);

}
