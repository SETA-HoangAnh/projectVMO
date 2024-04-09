package com.example.project.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class SignupRequest {

    private String userName;

    private String password;

    private String codingLanguage;

    @NotBlank
    @Email
    private String email;

    private Set<String> role;

    private Long centerId;
}
