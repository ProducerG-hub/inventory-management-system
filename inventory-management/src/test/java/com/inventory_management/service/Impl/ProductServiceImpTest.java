package com.inventory_management.service.Impl;

import com.inventory_management.dto.response.ProductResponseDTO;
import com.inventory_management.entity.Product;
import com.inventory_management.mapper.ProductMapper;
import com.inventory_management.repository.ProductRepository;
import com.inventory_management.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImpTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldReturnProductWhenProductExists(){
        Product product = new Product();
        product.setProductId(1);

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setProductId(1);

        when(productRepository.findById(1))
                .thenReturn(Optional.of(product));
        when(productMapper.toResponse(product))
                .thenReturn(productResponseDTO);

        ProductResponseDTO result = productService.getProductById(1);
        assertThat(result).isNotNull();
        assertThat(result.getProductId()).isEqualTo(1);

        verify(productRepository).findById(1);
        verify(productMapper).toResponse(product);
    }
}
