package com.example.project.dto;

import com.example.project.service.UserGradeServiceImpl;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.util.List;

@Data
@NoArgsConstructor
public class UserGradeDto {

    private Long userId;

    private String userName;

    private String fullName;

    private Long exercise1;

    private Long exercise2;

    private Long exercise3;

    @Transient
    private String averageScore;

//    private List<UserGradeInforDto> userGradeInforDtoList;

    public UserGradeDto(Long userId, String userName, String fullName, Long exercise1, Long exercise2,
                        Long exercise3)
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
