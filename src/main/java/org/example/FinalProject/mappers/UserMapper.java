package org.example.FinalProject.mappers;

import org.example.FinalProject.dto.UserDTO;
import org.example.FinalProject.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AddressMapper.class})
public interface UserMapper {
    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public UserEntity userDTOToEntity (UserDTO dto);
    public UserDTO userEntityToDTO (UserEntity entity);


}
