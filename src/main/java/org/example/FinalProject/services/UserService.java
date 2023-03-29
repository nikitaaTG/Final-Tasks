package org.example.FinalProject.services;

import org.example.FinalProject.dto.UserDTO;
import org.example.FinalProject.enums.RoleOnSite;
import org.example.FinalProject.mappers.UserMapper;
import org.example.FinalProject.models.UserEntity;
import org.example.FinalProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserMapper.INSTANCE::userEntityToDTO);
    }

    public UserDTO getUserByEmail(String email) {
        return UserMapper.INSTANCE.userEntityToDTO(userRepository.findByEmail(email));
    }

    /**
     * Method for creating user in DB. Here we set default values of role and deletedFlag.
     *
     * @param userDTO
     */
    public UserEntity createUser(UserDTO userDTO) {
        userDTO.setRole(RoleOnSite.CLIENT);
        userDTO.setUserDeleted(false);
        UserEntity userEntity = UserMapper.INSTANCE.userDTOToEntity(userDTO);
        return userRepository.save(userEntity);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    /**
     * Method for updating user in DB. Here we get parameters of sql-request and place them in right order.
     *
     * @param id
     * @param updatedUser
     */
    public void updateUser(long id, UserDTO updatedUser) {
        String name = updatedUser.getName();
        String surname = updatedUser.getSurname();
        Date birthDay = updatedUser.getBirthDay();
        String email = updatedUser.getEmail();
        userRepository.updateUser(name, surname, birthDay, email, id);
    }

    /**
     * Method for update users password in DB. Here we get parameters of sql-request and place them in right order.
     *
     * @param id
     * @param password
     */
    public void updatePassword(long id, String password) {
        userRepository.updatePassword(password, id);
    }

    public void deleteByID(Long id) {
        userRepository.deleteById(id);
    }
}
