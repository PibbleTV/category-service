package com.pibbletv.category_service.controller;

import com.pibbletv.category_service.business.interfaces.CategoryService;
import com.pibbletv.category_service.domain.Category;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "getAll")
    public Flux<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
