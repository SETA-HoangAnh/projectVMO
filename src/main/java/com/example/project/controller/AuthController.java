package com.example.project.controller;

import com.example.project.entity.ERole;
import com.example.project.entity.Role;
import com.example.project.entity.UserRole;
import com.example.project.entity.Users;
import com.example.project.payload.JWTResponse;
import com.example.project.payload.LoginRequest;
import com.example.project.payload.MessageResponse;
import com.example.project.payload.SignupRequest;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;
import com.example.project.repository.UserRoleRepository;
import com.example.project.security.jwt.JwtUtils;
import com.example.project.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;


    @Autowired
    public AuthController(UserRepository userRepository, UserRoleRepository userRoleRepository,
                          RoleRepository roleRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<Users> users = userRepository.findByUserName(loginRequest.getUserName());
        if (users.isPresent()) {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            return ResponseEntity.ok(new JWTResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail()
            ));

        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Không tìm thấy người dùng"));
        }
    }


    @PostMapping("/createUser")
    @PreAuthorize("@userServiceImpl.getRoles().contains('ROLE_ADMIN') " +
            "|| @userServiceImpl.getRoles().contains('ROLE_MANAGER') ")
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signUpRequest) {
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

        Users user = new Users(signUpRequest.getUserName(),
                signUpRequest.getEmail(),
                signUpRequest.getCodingLanguage(),
                encoder.encode(signUpRequest.getPassword())
                );

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName(ERole.ROLE_FRESHER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
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

//        userRepository.save(user);

        Users userSave = userRepository.save(user);
        for (Role role : roles) {
            UserRole userRole = new UserRole();
            userRole.setUser(userSave);
            userRole.setRole(role);
            userRoleRepository.save(userRole);
        }

//        Users centerSave = new Users();
//        centerSave.setCenter(userSave);
//


        return ResponseEntity.ok(new MessageResponse("Create user successfully!"));
    }
}
