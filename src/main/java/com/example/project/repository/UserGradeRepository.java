package com.example.project.repository;

import com.example.project.entity.UserGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGradeRepository extends JpaRepository<UserGrade, Long> {
}
