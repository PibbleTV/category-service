package com.pibbletv.category_service.controller;

import com.pibbletv.category_service.business.interfaces.CategoryService;
import com.pibbletv.category_service.domain.Category;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173" , "http://localhost:4173"})
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(value = "getAll")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
