//package com.pibbletv.category_service.unit;
//
//import com.pibbletv.category_service.business.converters.CategoryConverter;
//import com.pibbletv.category_service.business.implementations.CategoryServiceImpl;
//import com.pibbletv.category_service.domain.Category;
//import com.pibbletv.category_service.persistance.entities.CategoryEntity;
//import com.pibbletv.category_service.persistance.repository.CategoryRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import java.util.Base64;
//import java.util.UUID;
//
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import reactor.test.StepVerifier;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
//public class CategoryServiceImplTest {
//
//
//    @Mock
//    private CategoryRepository categoryRepository;
//
//    @InjectMocks
//    private CategoryServiceImpl categoryService;
//
//    String base64Image = Base64.getEncoder().encodeToString(new byte[]{0x1, 0x2, 0x3});
//
//    UUID uuid = UUID.randomUUID();
//    UUID uuid1 = UUID.randomUUID();
//    UUID uuid2 = UUID.randomUUID();
//
//    @Test
//    void testGetAllCategories_AllCategories_FetchedSuccessfully() {
//
//        CategoryEntity entity1 = new CategoryEntity(1L, uuid1, "GTA V", new byte[]{0x1, 0x2, 0x3});
//        CategoryEntity entity2 = new CategoryEntity(2L, uuid2, "Fortnite", new byte[]{0x4, 0x5, 0x6});
//
//        when(categoryRepository.findAll()).thenReturn(Flux.just(entity1, entity2));
//
//        StepVerifier.create(categoryService.getAllCategories())
//                .assertNext(category -> {
//                    assertNotNull(category);
//                    assertEquals(1L, category.getId());
//                    assertEquals(uuid1, category.getCategoryId());
//                    assertEquals("GTA V", category.getName());
//                    assertNotNull(category.getImage());
//                })
//                .assertNext(category -> {
//                    assertNotNull(category);
//                    assertEquals(2L, category.getId());
//                    assertEquals(uuid2, category.getCategoryId());
//                    assertEquals("Fortnite", category.getName());
//                    assertNotNull(category.getImage());
//                })
//                .verifyComplete();
//    }
//
//    @Test
//    void testGetAllCategories_EmptyList() {
//        when(categoryRepository.findAll()).thenReturn(Flux.empty());
//
//        StepVerifier.create(categoryService.getAllCategories())
//                .expectNextCount(0)
//                .verifyComplete();
//    }
//
//    @Test
//    void testGetCategoriesByKeyword_CategoriesFound() {
//
//        CategoryEntity entity = new CategoryEntity(1L, uuid, "Call of Duty", new byte[]{0x1, 0x2, 0x3});
//        when(categoryRepository.findByKeyword("Call")).thenReturn(Flux.just(entity));
//
//        StepVerifier.create(categoryService.getCategoriesByKeyword("Call"))
//                .assertNext(category -> {
//                    assertEquals(1L, category.getId());
//                    assertEquals(uuid, category.getCategoryId());
//                    assertEquals("Call of Duty", category.getName());
//                })
//                .verifyComplete();
//    }
//
//    @Test
//    void testGetCategoriesByKeyword_NoMatch() {
//        when(categoryRepository.findByKeyword("Unknown")).thenReturn(Flux.empty());
//
//        StepVerifier.create(categoryService.getCategoriesByKeyword("Unknown"))
//                .expectNextCount(0)
//                .verifyComplete();
//    }
//
//    @Test
//    void testAddCategory_SuccessfullySaved() {
//        Category category = new Category(3L, uuid, "Minecraft", base64Image);
//        CategoryEntity entity = CategoryConverter.convertToEntity(category);
//
//        when(categoryRepository.save(entity)).thenReturn(Mono.empty());
//
//        StepVerifier.create(categoryService.addCategory(category))
//                .verifyComplete();
//    }
//
//    @Test
//    void testAddCategory_SaveFails() {
//
//        Category category = new Category(4L, uuid, "Broken Category", base64Image);
//        CategoryEntity entity = CategoryConverter.convertToEntity(category);
//
//        when(categoryRepository.save(entity)).thenReturn(Mono.error(new RuntimeException("DB error")));
//
//        StepVerifier.create(categoryService.addCategory(category))
//                .expectErrorMessage("DB error")
//                .verify();
//    }
//
//    @Test
//    void testUpdateCategory_Success() {
//
//        Category category = new Category(5L, uuid, "Call of Duty: Black Ops III", base64Image);
//        CategoryEntity entity = CategoryConverter.convertToEntity(category);
//
//        when(categoryRepository.save(entity)).thenReturn(Mono.empty());
//
//        StepVerifier.create(categoryService.updateCategory(category))
//                .verifyComplete();
//    }
//
//    @Test
//    void testUpdateCategory_SaveFails() {
//        Category category = new Category(6L, uuid1, "Fail Game", base64Image);
//        CategoryEntity entity = new CategoryEntity(6L, uuid1, "Fail Game", new byte[]{0x1, 0x2, 0x3});
//
//        when(categoryRepository.save(entity)).thenReturn(Mono.error(new RuntimeException("Save failed")));
//
//        StepVerifier.create(categoryService.updateCategory(category))
//                .expectErrorMessage("Save failed")
//                .verify();
//    }
//
//    @Test
//    void testDeleteCategory_CategoryNotFound() {
//        Long id = 8L;
//
//        when(categoryRepository.findById(id)).thenReturn(Mono.empty());
//
//        StepVerifier.create(categoryService.deleteCategory(id))
//                .expectErrorMatches(throwable ->
//                        throwable instanceof Exception &&
//                                throwable.getMessage().equals("Category not found"))
//                .verify();
//    }
//
//
//
//}
//
