package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.util.List;

@Data
@NoArgsConstructor
public class ScoreListDetailDto {

    private Long exercise1;

    private Long exercise2;

    private Long exercise3;

    @Transient
    private List<UserInforClassDto> userInforClassDtoList;

    public ScoreListDetailDto(Long exercise1, Long exercise2, Long exercise3) {
        this.exercise1 = exercise1;
        this.exercise2 = exercise2;
        this.exercise3 = exercise3;
        this.userInforClassDtoList = null;
    }
}
