package com.example.project.service;

import com.example.project.dto.UserInforDto;
import com.example.project.dto.UserInforNoCenterDTO;
import com.example.project.entity.ProjectUser;
import com.example.project.entity.UserGrade;
import com.example.project.entity.Users;
import com.example.project.payload.SignupRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    Page<UserInforDto> getUser(String userName, String fullName, String codingLanguage, String email,
                               Pageable pageable);

    Page<UserInforNoCenterDTO> getUserNoCenter(String userName, String fullName, String codingLanguage, String email,
                                               Pageable pageable);

    ResponseEntity<?> createUser(SignupRequest signUpRequest, UserGrade userGrade);

    void editUser(Long userId, Users users);

    void deleteUser(Long userId);

    void tranferUser(Long userId, Users users);

    void removeFromCenter(Long userId, Long centerId);

}
