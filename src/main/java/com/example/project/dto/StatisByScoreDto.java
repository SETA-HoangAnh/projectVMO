package com.example.project.dto;

import com.example.project.entity.UserGrade;
import com.example.project.service.StatisServiceImpl;
import com.example.project.service.UserServiceImpl;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.util.List;

@Data
@NoArgsConstructor
public class StatisByScoreDto {

    private Long userGradeId;

    @Transient
    private List<ScoreListDetailDto> scoreList;


    public StatisByScoreDto(Long userGradeId) {
        this.userGradeId = userGradeId;
        this.scoreList = StatisServiceImpl.scoreList();
    }

}
