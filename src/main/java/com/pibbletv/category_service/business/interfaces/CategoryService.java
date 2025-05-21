package com.pibbletv.category_service.business.interfaces;

import com.pibbletv.category_service.domain.Category;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CategoryService {
    Flux<Category> getAllCategories();

    Flux<Category> getCategoriesByKeyword(String name);

    Mono<Void> addCategory(Category category);

    Mono<Void> updateCategory(Category category);

    Mono<Void> deleteCategory(String categoryId);
}
