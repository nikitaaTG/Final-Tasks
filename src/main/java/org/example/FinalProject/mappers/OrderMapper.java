package org.example.FinalProject.mappers;

import org.example.FinalProject.dto.OrderDTO;
import org.example.FinalProject.models.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductMapper.class, AddressMapper.class, UserMapper.class})
public interface OrderMapper {
    public static final OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    public OrderEntity orderDTOToEntity (OrderDTO dto);
    public OrderDTO orderEntityToDTO (OrderEntity entity);


}
