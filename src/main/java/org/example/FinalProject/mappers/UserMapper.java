package org.example.FinalProject.mappers;

import org.example.FinalProject.dto.CategoryDTO;
import org.example.FinalProject.dto.UserDTO;
import org.example.FinalProject.models.CategoryEntity;
import org.example.FinalProject.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public UserEntity userDTOToEntity (UserDTO dto);
    public UserDTO userEntityToDTO (UserEntity entity);


}
