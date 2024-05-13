package com.example.project.dto;

import com.example.project.service.Impl.StatisServiceImpl;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StatisByCenterDto {

    private Long centerId;

    private String centerName;

    private Long fresherNumber;

    private List<UserDetailDto> userDetailDtoList;

    public StatisByCenterDto(Long centerId, String centerName) {
        this.centerId = centerId;
        this.centerName = centerName;
        this.fresherNumber = StatisServiceImpl.countFresher(this.centerId);
        this.userDetailDtoList = StatisServiceImpl.userDetailDtoList(this.centerId);
    }
}
