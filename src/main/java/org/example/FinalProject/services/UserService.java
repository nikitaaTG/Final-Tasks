package org.example.FinalProject.services;

import org.example.FinalProject.models.UserEntity;
import org.example.FinalProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findByID (Long id) {
        return userRepository.getReferenceById(id);

    }

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public UserEntity createUser (UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public void deleteByID (Long id) {
        userRepository.deleteById(id);
    }
}
