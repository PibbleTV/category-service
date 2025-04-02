package com.pibbletv.category_service.business.interfaces;

import com.pibbletv.category_service.domain.Category;

import reactor.core.publisher.Flux;


public interface CategoryService {
    Flux<Category> getAllCategories();
}
