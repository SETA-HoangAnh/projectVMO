package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInforClassDto {

    private Long userId;

    private String userName;

    private String fullName;

    private String codingLanguage;

    private String email;

    private String centerName;

}
