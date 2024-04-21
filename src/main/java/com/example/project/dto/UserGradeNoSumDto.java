package com.example.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserGradeNoSumDto {

    private Long userId;

    private String userName;

    private String fullName;

    private Long exercise1;

    private Long exercise2;

    private Long exercise3;


    public UserGradeNoSumDto(Long userId, String userName, String fullName, Long exercise1, Long exercise2,
                        Long exercise3)
    {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.exercise1 = exercise1;
        this.exercise2 = exercise2;
        this.exercise3 = exercise3;
    }
}
