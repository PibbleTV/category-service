package com.pibbletv.category_service.persistance.repository;

import com.pibbletv.category_service.persistance.entities.CategoryEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<CategoryEntity, Long> {
}
