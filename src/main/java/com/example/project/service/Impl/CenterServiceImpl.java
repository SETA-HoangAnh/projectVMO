package com.example.project.service.Impl;

import com.example.project.entity.Center;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.CenterRepository;
import com.example.project.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {

    private final CenterRepository centerRepository;

    @Override
    public Page<Center> getAllCenter(String centerName, Pageable pageable) {

        if(centerName == null){
            centerName = "";
        }
        return centerRepository.getCenter(centerName, pageable);
    }


    @Override
    public void save(Center center) {

        centerRepository.save(center);
    }


    @Override
    public void update(Long centerId ,Center center) {

        Center centerFind = centerRepository.findById(centerId)
                .orElseThrow(() -> new ResourceNotFoundException("Center", "Id", centerId));
        centerFind.setCenterName(center.getCenterName());
        centerRepository.save(centerFind);
    }


    @Override
    public void delete(Long centerId) {

        centerRepository.deleteById(centerId);
    }
}
