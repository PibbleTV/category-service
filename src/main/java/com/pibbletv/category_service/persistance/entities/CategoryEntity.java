package com.pibbletv.category_service.persistance.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("categories")
public class CategoryEntity {

    @Id
    private Long id;

    @NotEmpty
    @Length(min = 1, max = 25)
    private String name;

    private byte[] image;
}