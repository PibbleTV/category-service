package com.pibbletv.category_service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Long id;

    private UUID categoryId;

    private String name;

    private String image;
}
