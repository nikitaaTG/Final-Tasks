package org.example.FinalProject.mappers;

import org.example.FinalProject.dto.ProductDTO;
import org.example.FinalProject.models.ProductEntity;
import org.example.FinalProject.services.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")

public abstract class ProductMapper {
    public static final ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Autowired
    ProductService productService;

    public abstract ProductEntity productDTOToEntity (ProductDTO dto);
    public abstract ProductDTO productEntityToDTO (ProductEntity entity);

}
