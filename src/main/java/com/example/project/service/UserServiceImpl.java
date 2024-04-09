package com.example.project.service;

import com.example.project.entity.UserRole;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;
import com.example.project.repository.UserRoleRepository;
import com.example.project.security.service.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
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
}
