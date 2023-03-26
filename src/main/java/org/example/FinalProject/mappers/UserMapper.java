package org.example.FinalProject.mappers;

import org.example.FinalProject.dto.UserDTO;
import org.example.FinalProject.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public UserEntity userDTOToEntity(
            UserDTO dto); // FIXME: кстати для краткости можно было создать базовый интерфейс с туДто и туЕнтити, которые бы принимали шаблоны. И были бы универсальные названия, но это скорее на будущее

    public UserDTO userEntityToDTO(UserEntity entity);


}
