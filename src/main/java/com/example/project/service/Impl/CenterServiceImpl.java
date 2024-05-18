package com.example.project.service.Impl;

import com.example.project.entity.Center;
import com.example.project.entity.Users;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.CenterRepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CenterServiceImpl implements CenterService {

    private final CenterRepository centerRepository;

    private final UserRepository userRepository;

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
    public ResponseEntity<String> delete(Long centerId) {

        Boolean idCheck = userRepository.existsByCenterCenterId(centerId);
        if(idCheck) {

            return ResponseEntity.badRequest().body("Still contains fresher");
        }
        else {

            Center centerFind = centerRepository.findById(centerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Center", "Id", centerId));
            centerRepository.deleteById(centerFind.getCenterId());
            return ResponseEntity.ok("Center deleted");
        }
    }
}
