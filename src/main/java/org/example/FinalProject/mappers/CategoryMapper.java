package org.example.FinalProject.mappers;

import org.example.FinalProject.dto.CategoryDTO;
import org.example.FinalProject.models.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    public abstract CategoryEntity categoryDTOToEntity (CategoryDTO dto);
    public abstract CategoryDTO categoryEntityToDTO (CategoryEntity entity);

    public abstract List<CategoryDTO> listDTO (List<CategoryEntity> list);

}
