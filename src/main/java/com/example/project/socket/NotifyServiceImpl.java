package com.example.project.socket;

import com.example.project.entity.Users;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class NotifyServiceImpl {

    private static NotifyRepository notifyRepository;

    private static UserRepository userRepository;

    private final ModelMapper modelMapper;

    public NotifyServiceImpl(NotifyRepository notifyRepository, ModelMapper modelMapper, UserRepository usersRepository) {
        NotifyServiceImpl.notifyRepository = notifyRepository;
        NotifyServiceImpl.userRepository = usersRepository;
        this.modelMapper = modelMapper;
    }

    public Object findAll(Long userID, String language) {
        Map<Object, Object> test = new HashMap<>();
        Users userFind = userRepository.findById(userID).orElseThrow(()->new ResourceNotFoundException("User","Id",userID));
        test.put("listNotify",notifyRepository.findAllNotifyByUser(userID));
        return test;
//        return null;
    }

    public Notify save(Notify entity) {
        return notifyRepository.save(entity);
    }

    public NotifyDTO findById(Long notifyId) {
        Optional<Notify> notifyFind = notifyRepository.findById(notifyId);
        if(notifyFind.isPresent()){
            notifyRepository.save(notifyFind.get());
            return notifyRepository.findNotifyById(notifyId);
//            return null;
        }else {
            throw new ResourceNotFoundException("Notify", "Id", notifyId);
        }
    }
}
