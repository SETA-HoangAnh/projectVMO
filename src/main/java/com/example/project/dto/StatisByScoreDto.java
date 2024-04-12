package com.example.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;

@Data
@NoArgsConstructor
public class StatisByScoreDto {


    @Transient
    private String score;

    public StatisByScoreDto(String score) {
        this.score = score;
    }
}
