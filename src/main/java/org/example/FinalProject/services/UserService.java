package org.example.FinalProject.services;

import org.example.FinalProject.dto.ProductDTO;
import org.example.FinalProject.dto.UserDTO;
import org.example.FinalProject.enums.RoleOnSite;
import org.example.FinalProject.mappers.UserMapper;
import org.example.FinalProject.models.UserEntity;
import org.example.FinalProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
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

    public Page<UserEntity> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public UserEntity createUser (UserDTO userDTO) {
        userDTO.setRole(RoleOnSite.CLIENT);
        userDTO.setUserDeleted(false);
        UserEntity userEntity = UserMapper.INSTANCE.userDTOToEntity(userDTO);
        return userRepository.save(userEntity);
    }

    public UserEntity getUserById (Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void updateUser(long id, UserDTO updatedUser) {
        String name = updatedUser.getName();
        String surname = updatedUser.getSurname();
        Date birthDay = updatedUser.getBirthDay();
        String email = updatedUser.getEmail();
        String password = updatedUser.getPassword();
        userRepository.updateUser(name, surname, birthDay, email, password, id);
    }
    public void deleteByID (Long id) {
        userRepository.deleteById(id);
    }
}
