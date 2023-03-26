package org.example.FinalProject.mappers;

import java.util.List;
import java.util.Set;

import org.example.FinalProject.dto.AddressDTO;
import org.example.FinalProject.models.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    public static final AddressMapper INSTANCE = Mappers.getMapper(
            AddressMapper.class); // FIXME: паблик и статик тут лишние

    public AddressEntity addressDTOToEntity(
            AddressDTO dto); // FIXME: паблик для интерфейсов не нужен. Проверь и остальные мапперы

    public AddressDTO addressEntityToDTO(AddressEntity entity);

    public List<AddressDTO> listDTO(List<AddressEntity> list);

    public Set<AddressDTO> setDTO(Set<AddressEntity> set);


}
