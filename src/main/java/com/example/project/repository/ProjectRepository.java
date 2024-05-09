package com.example.project.repository;

import com.example.project.dto.ProjectDto;
import com.example.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(nativeQuery = true,
    value = """
            SELECT p.project_id as projectId, p.project_name as projectName, p.project_code as projectCode, 
            p.start_date as startDate, p.end_date as endDate, p.coding_language as codingLanguage, 
            p.project_status as projectStatus 
            FROM project p 
            WHERE p.project_name like %?1% and p.project_code like %?2% """,
    countQuery = """
            SELECT count(p.project_id) FROM project p WHERE p.project_name like %?1% and p.project_code like %?2% """)
    Page<ProjectDto> getProject(String projectName, String projectCode, Pageable pageable);
}
