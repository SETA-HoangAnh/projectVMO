package com.example.project.repository;

import com.example.project.dto.ProjectAndUserDto;
import com.example.project.dto.ProjectDto;
import com.example.project.entity.ProjectUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {

    @Query(nativeQuery = true,
    value = """
            SELECT pu.project_user_id, p.project_id, u.user_id FROM project_user pu 
            INNER JOIN project p on p.project_id = pu.project_id 
            INNER JOIN users u on u.user_id = pu.user_id 
            WHERE p.project_id = ?1 and u.user_id = ?2 """)
    ProjectUser findProjectByUser(Long projectId, Long userId);

    @Query(nativeQuery = true,
    value = """
            SELECT p.project_id as projectId, u.user_id as userId, u.user_name as userName, u.full_name as fullName, 
            u.coding_language as codingLanguage, u.email as email 
            FROM users u 
            INNER JOIN project_user pu on pu.user_id = u.user_id 
            INNER JOIN project p on p.project_id = pu.project_id 
            WHERE p.project_id = ?1 """,
    countQuery = """
            SELECT count(u.user_id) FROM users u 
            INNER JOIN project_user pu on pu.user_id = u.user_id 
            INNER JOIN project p on p.project_id = pu.project_id 
            WHERE p.project_id = ?1 """)
    Page<ProjectAndUserDto> listProjectAndUser(Long projectId, Pageable pageable);

    @Query(nativeQuery = true,
            value = """
                    SELECT p.project_id as projectId, p.project_name as projectName, p.project_code as projectCode, 
                    p.start_date as startDate, p.end_date as endDate, p.coding_language as codingLanguage, 
                    p.project_status as projectStatus 
                    FROM project p 
                    INNER JOIN project_user pu on pu.project_id = p.project_id 
                    INNER JOIN users u on u.user_id = pu.user_id 
                    WHERE u.user_id = ?1""",
    countQuery = """
            SELECT count(p.project_id) FROM project p 
            INNER JOIN project_user pu on pu.project_id = p.project_id 
            INNER JOIN users u on u.user_id = pu.user_id 
            WHERE u.user_id = ?1 """)
    Page<ProjectDto> listProjectByUser(Long userId, Pageable pageable);
}
