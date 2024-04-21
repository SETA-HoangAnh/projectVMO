package com.example.project.dto;

import com.example.project.service.StatisServiceImpl;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class StatisByAverageDto {

    private List<Long> score;

    private List<UserInforNoCenterDTO> user;

    public StatisByAverageDto() {
        this.score = StatisServiceImpl.scoreList2();
    }
}
