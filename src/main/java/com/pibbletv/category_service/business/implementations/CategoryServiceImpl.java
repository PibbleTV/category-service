package com.pibbletv.category_service.business.implementations;

import com.pibbletv.category_service.business.converters.CategoryConverter;
import com.pibbletv.category_service.business.interfaces.CategoryService;
import com.pibbletv.category_service.domain.Category;
import com.pibbletv.category_service.persistance.jpa.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryConverter::convertToObject)
                .toList();
    }
}
