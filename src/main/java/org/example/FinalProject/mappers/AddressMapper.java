package org.example.FinalProject.mappers;

import org.example.FinalProject.dto.AddressDTO;
import org.example.FinalProject.models.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    public static final AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    public AddressEntity addressDTOToEntity (AddressDTO dto);
    public AddressDTO addressEntityToDTO (AddressEntity entity);

    public List<AddressDTO> listDTO(List<AddressEntity> list);

    public Set<AddressDTO> setDTO(Set<AddressEntity> set);


}
