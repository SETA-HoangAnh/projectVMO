package com.example.project.repository;

import com.example.project.dto.UserGradeDto;
import com.example.project.entity.UserGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGradeRepository extends JpaRepository<UserGrade, Long> {

    @Query("select new com.example.project.dto.UserGradeDto(" +
            "u.userId, u.userName, u.fullName, ug.exercise1, ug.exercise2, ug.exercise3" +
            ")" +
            "from UserGrade ug " +
            "left join ug.users u where u.userId = ?1 ")
    UserGradeDto getGrade(Long userId);


    @Query(nativeQuery = true,
    value = "SELECT ug.user_grade_id " +
            "FROM user_grade ug " +
            "INNER JOIN users u on u.user_id = ug.user_id " +
            "WHERE u.user_id = ?1 ")
    Long getGrade2(Long userId);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM user_grade ug " +
                    "WHERE ug.user_grade_id = ?1 ")
    UserGrade findGradebyId(Long userGradeId);


//    SELECT r.roleId FROM User u, UserRole r where r.userId = u.userId and u.name = "Jonh Smith"

}
