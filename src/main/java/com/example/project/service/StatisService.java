package com.example.project.service;

import com.example.project.dto.StatisByCenterDto;
import com.example.project.dto.UserInforNoCenterDTO;

import java.util.List;

public interface StatisService {

    List<StatisByCenterDto> getByCenter(Long centerId);

    List<UserInforNoCenterDTO> listInforByAverage(Long averageScore);
}
