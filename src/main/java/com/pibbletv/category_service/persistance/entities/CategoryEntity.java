package com.pibbletv.category_service.persistance.entities;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("categories")
public class CategoryEntity {

    @Id
    @Column("id")
    private Long id;

    @NotNull
    @Column("categoryId")
    private String categoryId;

    @NotEmpty
    @Length(min = 1, max = 25)
    @Column("name")
    private String name;

    @Column("image")
    private byte[] image;
}