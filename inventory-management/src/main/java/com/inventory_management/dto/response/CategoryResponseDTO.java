package com.inventory_management.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {


    private Integer categoryId;


    private String categoryName;


    private String description;


    private Boolean active;


    private LocalDateTime deletedAt;

}