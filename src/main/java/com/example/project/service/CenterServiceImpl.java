package com.example.project.service;

import com.example.project.entity.Center;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.CenterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CenterServiceImpl {

    private final CenterRepository centerRepository;

    public CenterServiceImpl(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    public Page<Center> getCenter(String centerName, Pageable pageable){

        if(centerName == null){
            centerName = "";
        }
        return centerRepository.getCenter(centerName, pageable);
    }

    public Center addCenter(Center center){

        return centerRepository.save(center);
    }

    public Center editCenter(Long centerId, Center center){

        Center centerFind = centerRepository.findById(centerId)
                .orElseThrow(()-> new ResourceNotFoundException("Center", "Id", centerId));
        centerFind.setCenterName(center.getCenterName());

        return centerRepository.save(centerFind);

    }

    public Center deleteCenter(Long centerId){

        Center centerFind = centerRepository.findById(centerId)
                .orElseThrow(()-> new ResourceNotFoundException("Center", "Id", centerId));
        centerRepository.deleteById(centerFind.getCenterId());

        return null;

    }
}
