package com.example.project.repository;

import com.example.project.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {

    @Query(nativeQuery = true,
    value = "SELECT * FROM center c " +
            "WHERE c.center_name like %?1% ")
    List<Center> getCenter(String centerName);

    @Query("SELECT CASE WHEN count(c) > 0 THEN true ELSE false END FROM Center c where c.centerId = ?1")
    Boolean existsByCenterId(Long centerId);

}
