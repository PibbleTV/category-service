package com.pibbletv.category_service.integration;

import com.pibbletv.category_service.business.converters.CategoryConverter;
import com.pibbletv.category_service.business.implementations.CategoryServiceImpl;
import com.pibbletv.category_service.domain.Category;
import com.pibbletv.category_service.persistance.entities.CategoryEntity;
import com.pibbletv.category_service.persistance.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Base64;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
public class CategoryControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    String base64Image = Base64.getEncoder().encodeToString(new byte[]{0x1, 0x2, 0x3});


    @Test
    void getAllCategories_shouldReturnCategories() {

        Category category1 = new Category(1L, "Gaming", base64Image);
        Category category2 = new Category(2L, "Music", base64Image);

        CategoryEntity categoryEntity1 = CategoryConverter.convertToEntity(category1);
        CategoryEntity categoryEntity2 = CategoryConverter.convertToEntity(category2);

        Flux<CategoryEntity> categoryEntities = Flux.just(categoryEntity1, categoryEntity2);
        Flux<Category> categories = categoryEntities.map(CategoryConverter::convertToObject);

        when(categoryRepository.findAll()).thenReturn(categoryEntities);
        when(categoryService.getAllCategories()).thenReturn(categories);

        webTestClient.get()
                .uri("/category/getAll")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Category.class)
                .hasSize(2)
                .contains(category1, category2);
    }

    @Test
    void getCategoriesByKeyword_shouldReturnFilteredCategories() {

        String keyword = "gaming";
        Category category = new Category(1L, "Gaming", base64Image);
        Flux<Category> categories = Flux.just(category);
        Flux<CategoryEntity> categoriesEntity = categories.map(CategoryConverter::convertToEntity);

        when(categoryRepository.findByKeyword(keyword)).thenReturn(categoriesEntity);
        when(categoryService.getCategoriesByKeyword(keyword)).thenReturn(categories);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/category/getByKeyword")
                        .queryParam("keyword", keyword)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Category.class)
                .hasSize(1)
                .contains(category);
    }

    @Test
    @WithMockUser(roles = "admin")
    void addCategory_shouldReturnStatusCreated_whenCategoryIsAdded() {

        Category category = new Category(null, "Movies", base64Image);
        CategoryEntity entity = CategoryConverter.convertToEntity(category);

        when(categoryRepository.save(entity)).thenReturn(Mono.empty());
        when(categoryService.addCategory(category)).thenReturn(Mono.empty());


        webTestClient.post()
                .uri("/category/addCategory")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .bodyValue(category)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @WithMockUser(roles = "admin")
    void updateCategory_shouldReturnStatusOk_whenCategoryIsUpdated() {

        Category category = new Category(1L, "Gaming", base64Image);
        CategoryEntity entity = CategoryConverter.convertToEntity(category);

        when(categoryRepository.save(entity)).thenReturn(Mono.empty());
        when(categoryService.updateCategory(category)).thenReturn(Mono.empty());

        webTestClient.put()
                .uri("/category/updateCategory")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .bodyValue(category)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @WithMockUser(roles = "admin")
    void deleteCategory_shouldReturnStatusOk_whenCategoryIsDeleted() {

        Long categoryId = 1L;

        Category category = new Category(1L, "Gaming", base64Image);
        CategoryEntity entity = CategoryConverter.convertToEntity(category);

        when(categoryRepository.findById(categoryId)).thenReturn(Mono.just(entity));
        when(categoryRepository.delete(entity)).thenReturn(Mono.empty());
        when(categoryService.deleteCategory(categoryId)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/category/deleteCategory")
                        .queryParam("categoryId", categoryId)
                        .build())
                .exchange()
                .expectStatus().isOk();
    }


    @Test
    void getAllCategories_shouldReturnEmptyList_whenNoCategoriesExist() {

        Flux<Category> categories = Flux.empty();
        Flux<CategoryEntity> categoriesEntity = categories.map(CategoryConverter::convertToEntity);

        when(categoryRepository.findAll()).thenReturn(categoriesEntity);
        when(categoryService.getAllCategories()).thenReturn((categories));

        webTestClient.get()
                .uri("/category/getAll")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Category.class)
                .hasSize(0);
    }

    @Test
    void getCategoriesByKeyword_shouldReturnEmpty_whenNoCategoriesMatch() {

        String keyword = "nonexistent";
        Flux<Category> categories = Flux.empty();


        when(categoryRepository.findByKeyword(keyword)).thenReturn(Flux.empty());
        when(categoryService.getCategoriesByKeyword(keyword)).thenReturn(categories);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/category/getByKeyword")
                        .queryParam("keyword", keyword)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Category.class)
                .hasSize(0);
    }

//    @Test
//    @WithMockUser(roles = "admin")
//    void addCategory_shouldReturnBadRequest_whenCategoryIsInvalid() {
//
//        Category invalidCategory = new Category(null, "", base64Image);
//
//        when(categoryService.addCategory(invalidCategory)).thenReturn(Mono.error(new IllegalArgumentException("Invalid category data")));
//
//
//        webTestClient.post()
//                .uri("/category/addCategory")
//                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
//                .bodyValue(invalidCategory)
//                .exchange()
//                .expectStatus().isBadRequest();
//    }
//
//    @Test
//    @WithMockUser(roles = "admin")
//    void updateCategory_shouldReturnBadRequest_whenCategoryDoesNotExist() {
//
//        Category category = new Category(999L, "Nonexistent Category", base64Image);
//        Mono<Void> monoVoid = Mono.error(new Exception("Category not found"));
//
//        when(categoryService.updateCategory(category)).thenReturn(monoVoid);
//
//        // When & Then
//        webTestClient.put()
//                .uri("/category/updateCategory")
//                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
//                .bodyValue(category)
//                .exchange()
//                .expectStatus().isNotFound();
//    }
//
//    @Test
//    @WithMockUser(roles = "admin")
//    void deleteCategory_shouldReturnNotFound_whenCategoryDoesNotExist() {
//
//        Long categoryId = 999L;
//        when(categoryService.deleteCategory(categoryId)).thenReturn(Mono.error(new Exception("Category not found")));
//
//
//        webTestClient.delete()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/category/deleteCategory")
//                        .queryParam("categoryId", categoryId)
//                        .build())
//                .exchange()
//                .expectStatus().isNotFound();
//    }

    @Test
    void addCategory_shouldReturnForbidden_whenUserHasNoAdminRole() {

        Category category = new Category(null, "Music", base64Image);


        webTestClient.post()
                .uri("/category/addCategory")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .bodyValue(category)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void deleteCategory_shouldReturnForbidden_whenUserHasNoAdminRole() {

        Long categoryId = 1L;

        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/category/deleteCategory")
                        .queryParam("categoryId", categoryId)
                        .build())
                .exchange()
                .expectStatus().isForbidden();
    }
}



