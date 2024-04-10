package com.example.project.service;

import com.example.project.entity.Center;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.CenterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CenterServiceImpl {

    private final CenterRepository centerRepository;

    public CenterServiceImpl(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    public ResponseEntity<?> getCenter(String centerName){

        if(centerName == null){
            centerName = "";
        }
        return ResponseEntity.ok(centerRepository.getCenter(centerName));
    }

    public ResponseEntity<?> addCenter(Center center){

        centerRepository.save(center);
        return ResponseEntity.ok("New center added");
    }

    public ResponseEntity<?> editCenter(Long centerId, Center center){

        Center centerFind = centerRepository.findById(centerId)
                .orElseThrow(()-> new ResourceNotFoundException("Center", "Id", centerId));
        centerFind.setCenterName(center.getCenterName());
        centerRepository.save(centerFind);
        return ResponseEntity.ok("Center updated");

    }

    public ResponseEntity<?> deleteCenter(Long centerId){

        Center centerFind = centerRepository.findById(centerId)
                .orElseThrow(()-> new ResourceNotFoundException("Center", "Id", centerId));
        centerRepository.deleteById(centerFind.getCenterId());
        return ResponseEntity.ok("Center deleted");

    }
}
