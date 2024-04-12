package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto {

    private Long userId;

    private String userName;

    private String fullName;

    private String codingLanguage;

    private Long centerId;
}
