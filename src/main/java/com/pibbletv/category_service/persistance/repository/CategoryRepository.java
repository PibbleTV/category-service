package com.pibbletv.category_service.persistance.repository;

import com.pibbletv.category_service.persistance.entities.CategoryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<CategoryEntity, Long> {
    @Query("SELECT * FROM categories WHERE LOWER(name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Flux<CategoryEntity> findByKeyword(@Param("keyword") String keyword);
}
