package org.example.FinalProject.services;

import org.example.FinalProject.models.User;
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

    public User findByID (BigInteger id) {
        return userRepository.getReferenceById(id);

    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User createUser (User user) {
        return userRepository.save(user);
    }

    public void deleteByID (BigInteger id) {
        userRepository.deleteById(id);
    }
}
