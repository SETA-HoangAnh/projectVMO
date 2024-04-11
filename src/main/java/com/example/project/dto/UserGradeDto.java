package com.example.project.dto;

import com.example.project.service.UserGradeServiceImpl;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;

@Data
@NoArgsConstructor
public class UserGradeDto {

    private Long userId;

    private String userName;

    private String fullName;

    private Double exercise1;

    private Double exercise2;

    private Double exercise3;

    @Transient
    private String averageScore;

    public UserGradeDto(Long userId, String userName, String fullName, Double exercise1, Double exercise2,
                        Double exercise3)
    {
        this.userId = userId;
        this.userName = userName;
        this.fullName = fullName;
        this.exercise1 = exercise1;
        this.exercise2 = exercise2;
        this.exercise3 = exercise3;
        this.averageScore = UserGradeServiceImpl.sumScore(this.userId);
    }
}
