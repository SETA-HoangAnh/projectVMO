package com.example.project.service;

import com.example.project.entity.Center;
import com.example.project.repository.CenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CenterServiceIm implements CenterService{
    private final CenterRepository centerRepository;

    @Override
    public Page<Center> getAllCenter(Pageable pageable) {
        return centerRepository.findAll(pageable);
    }

    @Override
    public Page<Center> getCentersByName(String centerName, Pageable pageable) {
        return centerRepository.findByCenterName(centerName,pageable);
    }

    @Override
    public void save(Center center) {
        centerRepository.save(center);
    }

    @Override
    public void delete(Long centerId) {
        centerRepository.deleteById(centerId);
    }
}
