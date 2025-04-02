package com.pibbletv.category_service.business.implementations;

import com.pibbletv.category_service.business.converters.CategoryConverter;
import com.pibbletv.category_service.business.interfaces.CategoryService;
import com.pibbletv.category_service.domain.Category;
import com.pibbletv.category_service.persistance.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Flux<Category> getAllCategories() {
        return categoryRepository.findAll()
                .map(CategoryConverter::convertToObject);
    }
}
