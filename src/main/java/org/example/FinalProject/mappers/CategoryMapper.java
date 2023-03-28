package org.example.FinalProject.mappers;

import org.example.FinalProject.dto.CategoryDTO;
import org.example.FinalProject.models.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {
    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    public CategoryEntity categoryDTOToEntity (CategoryDTO dto);
    public CategoryDTO categoryEntityToDTO (CategoryEntity entity);

    public List<CategoryDTO> listDTO (List<CategoryEntity> list);

}
