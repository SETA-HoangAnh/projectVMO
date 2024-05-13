package com.example.project.repository;

import com.example.project.dto.StatisByCenterDto;
import com.example.project.dto.UserGradeDto;
import com.example.project.entity.Center;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {

    @Query(nativeQuery = true,
    value = """
            SELECT * FROM center c 
            WHERE c.center_name like %?1% """,
    countQuery = """
            SELECT count(center_id) FROM center c WHERE c.center_name like %?1% """)
    Page<Center> getCenter(String centerName, Pageable pageable);


//    @Query(nativeQuery = true,
//    value = "SELECT c.center_id, c.center_name FROM center c WHERE c.center_id = ?1")
//    StatisByCenterDto getByCenter(Long centerId);

    @Query("""
            select new com.example.project.dto.UserGradeDto(
            u.userId, u.userName, u.fullName, ug.exercise1, ug.exercise2, ug.exercise3
            ) 
            from UserGrade ug  
            left join ug.users u where u.userId = ?1 """)
    UserGradeDto getGrade(Long userId);

    @Query("""
            select new com.example.project.dto.StatisByCenterDto( 
            c.centerId, c.centerName
            )
            from Center c 
            where (:centerId IS NULL OR c.centerId like CONCAT('%', :centerId, '%'))""")
    List<StatisByCenterDto> getByCenter(Long centerId);

    Boolean existsByCenterId(Long centerId);

    Page<Center> findByCenterName(String centerName, Pageable pageable);
}
