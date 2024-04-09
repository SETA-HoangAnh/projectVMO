package com.example.project.repository;

import com.example.project.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUserName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);
}
