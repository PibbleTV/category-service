package com.pibbletv.category_service.controller;

import com.pibbletv.category_service.business.interfaces.CategoryService;
import com.pibbletv.category_service.domain.Category;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "/getAll")
    public Flux<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(value = "/getByKeyword")
    public  Flux<Category> getCategoriesByKeyword(@RequestParam String keyword) {

        return categoryService.getCategoriesByKeyword(keyword);
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping(value = "/addCategory")
    public Mono<Void> addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping(value = "/updateCategory")
    public Mono<Void> editCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping(value = "/deleteCategory")
    public Mono<Void> deleteCategory(@RequestParam Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

}

