package com.example.project.repository;

import com.example.project.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(nativeQuery = true,
    value = "SELECT * FROM users u WHERE u.user_name like %?1% and u.full_name like %?2% ")
    List<Users> getUser(String userName, String fullName);

    Optional<Users> findByUserName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUserId(Long userId);
}
