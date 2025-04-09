package com.pibbletv.category_service.unit;

import com.pibbletv.category_service.business.converters.CategoryConverter;
import com.pibbletv.category_service.business.implementations.CategoryServiceImpl;
import com.pibbletv.category_service.domain.Category;
import com.pibbletv.category_service.persistance.entities.CategoryEntity;
import com.pibbletv.category_service.persistance.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class CategoryServiceImplTest {


    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;


    @Test
    void testGetAllCategories_AllCategories_FetchedSuccessfully() {
        // Given
        CategoryEntity entity1 = new CategoryEntity(1L, "GTA V", new byte[]{0x1, 0x2, 0x3});
        CategoryEntity entity2 = new CategoryEntity(2L, "Fortnite", new byte[]{0x4, 0x5, 0x6});

        when(categoryRepository.findAll()).thenReturn(Flux.just(entity1, entity2));

        // When
        Flux<Category> result = categoryService.getAllCategories();

        // Then
        StepVerifier.create(result)
                .assertNext(category -> {
                    assertNotNull(category);
                    assertEquals(1L, category.getId());
                    assertEquals("GTA V", category.getName());
                    assertNotNull(category.getImage());
                })
                .assertNext(category -> {
                    assertNotNull(category);
                    assertEquals(2L, category.getId());
                    assertEquals("Fortnite", category.getName());
                    assertNotNull(category.getImage());
                })
                .verifyComplete();
    }

    @Test
    void testGetAllCategories_EmptyList() {
        when(categoryRepository.findAll()).thenReturn(Flux.empty());

        StepVerifier.create(categoryService.getAllCategories())
                .expectNextCount(0)
                .verifyComplete();
    }
}

