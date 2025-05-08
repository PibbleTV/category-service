package com.pibbletv.category_service.business.implementations;

import com.pibbletv.category_service.business.converters.CategoryConverter;
import com.pibbletv.category_service.business.interfaces.CategoryService;
import com.pibbletv.category_service.domain.Category;
import com.pibbletv.category_service.persistance.entities.CategoryEntity;
import com.pibbletv.category_service.persistance.repository.CategoryRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Flux<Category> getAllCategories() {
        return categoryRepository.findAll()
                .map(CategoryConverter::convertToObject);
    }

    @Override
    public Flux<Category> getCategoriesByKeyword(String keyword) {
        return categoryRepository.findByKeyword(keyword)
                .map(CategoryConverter::convertToObject);
    }

    @Override
    public Mono<Void> addCategory(Category category) {

        CategoryEntity categoryEntity = CategoryConverter.convertToEntity(category);


        return categoryRepository.save(categoryEntity)
                .then();
    }

    @Override
    public Mono<Void> updateCategory(Category category) {

        CategoryEntity categoryEntity = CategoryConverter.convertToEntity(category);
        return categoryRepository.save(categoryEntity)
                .then();
    }

    @Override
    public Mono<Void> deleteCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .flatMap(categoryEntity -> categoryRepository.delete(categoryEntity)
                        .then())
                .switchIfEmpty(Mono.error(new Exception("Category not found")));
    }
}
