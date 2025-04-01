package com.pibbletv.category_service.business.interfaces;

import com.pibbletv.category_service.domain.Category;

import java.util.List;

public interface CategoryService {
    Flux<Category> getAllCategories();
}
