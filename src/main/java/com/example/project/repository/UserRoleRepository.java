package com.example.project.repository;

import com.example.project.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query(value = "select u from UserRole u where u.user.userId = ?1 group by u.role")
    List<UserRole> getRoles(Long userId);
}
