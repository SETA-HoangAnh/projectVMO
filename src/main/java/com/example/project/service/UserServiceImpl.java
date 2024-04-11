package com.example.project.service;

import com.example.project.dto.UserAndRoleDto;
import com.example.project.entity.*;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.payload.MessageResponse;
import com.example.project.payload.SignupRequest;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserGradeRepository;
import com.example.project.repository.UserRepository;
import com.example.project.repository.UserRoleRepository;
import com.example.project.security.service.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final UserGradeRepository userGradeRepository;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, RoleRepository roleRepository, PasswordEncoder encoder, UserGradeRepository userGradeRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.userGradeRepository = userGradeRepository;
    }


    public ResponseEntity<?> getUser(String userName, String fullName, String codingLanguage, String email){

        if(userName == null){

            userName = "";
        }
        if(fullName == null){

            fullName = "";
        }
        if(codingLanguage == null){

            codingLanguage = "";
        }
        if(email == null){

            email = "";
        }
        return ResponseEntity.ok(userRepository.getUser(userName, fullName, codingLanguage, email));
    }


    public ResponseEntity<?> createUser(SignupRequest signUpRequest, UserGrade userGrade) {
        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        Users users = new Users();
        users.setUserName(signUpRequest.getUserName());
        users.setFullName(signUpRequest.getFullName());
        users.setEmail(signUpRequest.getEmail());
        users.setCodingLanguage(signUpRequest.getCodingLanguage());
        users.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName(ERole.ROLE_FRESHER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
                    case "manager":
                        Role maRole = roleRepository.findByRoleName(ERole.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(maRole);

                        break;
                    case "fresher":
                        Role feRole = roleRepository.findByRoleName(ERole.ROLE_FRESHER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(feRole);

                        break;
                }
            });
        }

        Users userSave = userRepository.save(users);
        for (Role role : roles) {
            UserRole userRole = new UserRole();
            userRole.setUser(userSave);
            userRole.setRole(role);
            userRoleRepository.save(userRole);
        }

        UserAndRoleDto rolefind = userRepository.roleNamefind(users.getUserId());
        String roleNamefind2 = rolefind.getRoleName();
        if(roleNamefind2.equals("ROLE_FRESHER")) {
            userGrade.setUserId(users.getUserId());
            Users userFind = userRepository.findById(userGrade.getUserId()).get();
            userGrade.setUsers(userFind);
            userGrade.setExercise1(null);
            userGrade.setExercise2(null);
            userGrade.setExercise3(null);

            userGradeRepository.save(userGrade);;
        }

        return ResponseEntity.ok(new MessageResponse("Create user successfully!"));
    }


    public ResponseEntity<?> editUser(Long userId, Users users){

        Users userFind = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        userFind.setUserName(users.getUserName());
        userFind.setFullName(users.getFullName());
        userFind.setPassword(encoder.encode(users.getPassword()));
        userFind.setCodingLanguage(users.getCodingLanguage());
        userFind.setEmail(users.getEmail());
        userFind.getCenter();
        userRepository.save(userFind);

        return ResponseEntity.ok("User Edited");
    }


    public ResponseEntity<?> deleteUser(Long userId){

        Users userFind = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepository.deleteById(userFind.getUserId());

        return ResponseEntity.ok("User deleted");
    }


    public ResponseEntity<?> tranferUser(Long userId, Users users){

        Users userFind = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userFind.getUserName();
        userFind.getFullName();
        userFind.getPassword();
        userFind.getCodingLanguage();
        userFind.getEmail();
        userFind.setCenter(users.getCenter());
        userRepository.save(userFind);

        return ResponseEntity.ok("User tranfered");
    }


    public List<String> getRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> result = new ArrayList<>();
        List<UserRole> findRole = userRoleRepository.getRoles(userDetails.getId());
        for (UserRole u : findRole) {
            result.add(roleRepository.findById(u.getRole().getRoleId()).get().getRoleName().toString());
        }
        return result;
    }


//    public static Long getUserLoginId() {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//        return userDetails.getId();
//    }
}
