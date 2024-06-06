package com.example.project.payload;

import com.example.project.entity.Center;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class SignupRequest {

    @NotEmpty(message = "missing user name")
    private String userName;

    @NotEmpty(message = "missing full name")
    private String fullName;

    @NotEmpty(message = "missing password")
    private String password;

    @NotEmpty(message = "missing coding language")
    private String codingLanguage;

    @NotEmpty(message = "missing email")
    @Email(message = "invalid email format")
    private String email;

    @NotEmpty(message = "missing role")
    private Set<String> role;

//    private Long centerId;
}
