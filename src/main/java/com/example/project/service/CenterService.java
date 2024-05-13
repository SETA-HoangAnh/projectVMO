package com.example.project.service;

import com.example.project.entity.Center;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface CenterService {
    Page<Center> getAllCenter(String centerName, Pageable pageable);

//    Page<Center> getCentersByName(String centerName, Pageable pageable);

    void save(Center center);

    void update(Long centerId, Center center);

    void delete(Long centerId);
}
