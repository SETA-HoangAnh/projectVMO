package com.example.project.repository;

import com.example.project.dto.*;
import com.example.project.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(nativeQuery = true,
    value = """
            SELECT u.user_id as userId, u.user_name as userName, u.full_name as fullName, 
            u.coding_language as codingLanguage, u.email as email, c.center_name as centerName 
            FROM users u 
            INNER JOIN center c 
            on c.center_id = u.center_id 
            WHERE u.user_name like %?1% and u.full_name like %?2% 
            and u.coding_language like %?3% and u.email like %?4% """,
    countQuery = """
            SELECT count(user_id) FROM users u INNER JOIN center c on c.center_id = u.center_id 
            WHERE u.user_name like %?1% and u.full_name like %?2% 
            and u.coding_language like %?3% and u.email like %?4% """)
    Page<UserInforDto> getUser(String userName, String fullName, String codingLanguage, String email, Pageable pageable);

    @Query(nativeQuery = true,
            value = """
            SELECT u.user_id as userId, u.user_name as userName, u.full_name as fullName, 
            u.coding_language as codingLanguage, u.email as email  
            FROM users u 
            WHERE u.user_name like %?1% and u.full_name like %?2% 
            and u.coding_language like %?3% and u.email like %?4% and u.center_id is null """,
            countQuery = """
            SELECT count(user_id) FROM users u  
            WHERE u.user_name like %?1% and u.full_name like %?2% 
            and u.coding_language like %?3% and u.email like %?4% and u.center_id is null """)
    Page<UserInforNoCenterDTO> getUserNoCenter(String userName, String fullName, String codingLanguage, String email, Pageable pageable);


    @Query(nativeQuery = true,
    value = """
            SELECT u.user_id as userId, r.role_name as roleName 
            FROM users u 
            INNER JOIN user_role ur on ur.user_id = u.user_id 
            INNER JOIN role r on r.role_id = ur.role_id 
            WHERE u.user_id = ?1 """)
    UserAndRoleDto roleNamefind(Long userId);


    @Query("""
            select count (u.userId) 
            from Users u 
            left join u.center c where c.centerId = ?1 """)
    Long countFresher(Long centerId);

    @Query("""
            select new com.example.project.dto.UserDetailDto(
            u.userId, u.userName, u.fullName, u.codingLanguage, c.centerId 
            )
            from Users u 
            left join u.center c where c.centerId = ?1 """)
    List<UserDetailDto> getUserDetail(Long centerId);

    @Query("""
            select new com.example.project.dto.UserInforClassDto(
            u.userId, u.userName, u.fullName, u.codingLanguage, u.email, c.centerName 
            )
            from Users u 
            inner join u.userGrade ug  
            inner join u.center c 
            where (ug.exercise1 + ug.exercise2 + ug.exercise3)/3 = ?1""")
    List<UserInforClassDto> listUIClassDto(Long averageScore);

    Optional<Users> findByUserName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUserId(Long userId);

    Boolean existsByCenterCenterId(Long centerId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = """
            UPDATE users u
            SET u.center_id = null 
            WHERE u.user_id = ?1 and u.center_id = ?2 """)
    void updateId(Long userId, Long centerId);

}
