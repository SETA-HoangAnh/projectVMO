package com.example.project.repository;

import com.example.project.dto.*;

import com.example.project.entity.UserGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserGradeRepository extends JpaRepository<UserGrade, Long> {

    @Query("""
            select new com.example.project.dto.UserGradeDto( 
            u.userId, u.userName, u.fullName, ug.exercise1, ug.exercise2, ug.exercise3
            )
            from UserGrade ug 
            left join ug.users u where u.userId = ?1 """)
    UserGradeDto getGrade(Long userId);

    @Query("""
            select new com.example.project.dto.UserGradeNoSumDto(
            u.userId, u.userName, u.fullName, ug.exercise1, ug.exercise2, ug.exercise3
            )
            from UserGrade ug 
            left join ug.users u where u.userId = ?1 """)
    UserGradeNoSumDto getGradeNoSum(Long userId);

    @Query(nativeQuery = true,
    value = """
            SELECT ug.user_grade_id 
            FROM user_grade ug 
            INNER JOIN users u on u.user_id = ug.user_id 
            WHERE u.user_id = ?1 """)
    Long getGrade2(Long userId);

    @Query(nativeQuery = true,
            value = """
                    SELECT * 
                    FROM user_grade ug 
                    WHERE ug.user_grade_id = ?1 """)
    UserGrade findGradebyId(Long userGradeId);

//    @Query("select (ug.exercise1 + ug.exercise2 + ug.exercise3)/3 " +
//            "from UserGrade ug " +
//            "group by (ug.exercise1 + ug.exercise2 + ug.exercise3)/3 ")
//    List<Long> scoreList();

    @Query(nativeQuery = true,
    value = """
            SELECT FLOOR((ug.exercise1 + ug.exercise2 + ug.exercise3)/3) 
            FROM user_grade ug 
            group by  FLOOR((ug.exercise1 + ug.exercise2 + ug.exercise3)/3)""")
    List<Long> scoreList();

    @Query(nativeQuery = true,
    value = """
            SELECT u.user_id as userId, u.user_name as userName, u.full_name as fullName, 
            u.coding_language as codingLanguage, u.email as email 
            FROM users u 
            INNER JOIN user_grade ug on u.user_id = ug.user_id 
            WHERE FLOOR((ug.exercise1 + ug.exercise2 + ug.exercise3)/3) = ?1""")
    List<UserInforNoCenterDTO> listInforByAverage(Long averageScore);

}
