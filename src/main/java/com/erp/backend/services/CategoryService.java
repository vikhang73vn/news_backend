package com.erp.backend.services;

import com.erp.backend.dtos.CategoryDto;
import com.erp.backend.dtos.mappers.CategoryDTOMapper;
import com.erp.backend.dtos.request.CategoryRequest;
import com.erp.backend.dtos.request.UpdateCategoryRequest;
import com.erp.backend.entities.Category;
import com.erp.backend.exceptions.ExitException;
import com.erp.backend.models.Response;
import com.erp.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryDTOMapper categoryDTOMapper;

public CategoryDto createCategory(@Valid CategoryRequest request){
    Optional<Category> optionalCategory = categoryRepository.findByNameIgnoreCase(request.getName());
if (optionalCategory.isPresent()){
throw new ExitException(HttpStatus.BAD_REQUEST,"Category is exits");
}
Category category = Category.builder()
        .name(request.getName())
        .build();
Category save= categoryRepository.save(category);

return categoryDTOMapper.apply(category);
}

   public CategoryDto updateCategory (UpdateCategoryRequest request){
    Optional<Category> optionalCategory =categoryRepository.findById(request.getId());
Category category= optionalCategory.get();
category.setName(request.getName());
       Category save=categoryRepository.save(category);
return categoryDTOMapper.apply(save);


   }
   public Response deleteCategory(Long idCategory){
Optional<Category> optionalCategory = categoryRepository.findById(idCategory);
Category category = optionalCategory.get();
categoryRepository.delete(category);
return new Response(200,null, null);
   }
   public List<CategoryDto> getAllCategory(){
List<Category> list = categoryRepository.findAll();
List<CategoryDto> categoryDtos =list.stream().map(categoryDTOMapper::apply).collect(Collectors.toList());
return  categoryDtos;
   }
   public CategoryDto getCategory(Long idCategory){
    return categoryDTOMapper.apply( categoryRepository.findById(idCategory).get());
   }

    public CategoryDto getCategory(String nameCategory){
        return categoryDTOMapper.apply( categoryRepository.findByNameIgnoreCase(nameCategory).get());
    }


}
