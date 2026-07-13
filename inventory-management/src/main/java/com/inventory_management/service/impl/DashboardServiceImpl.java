package com.inventory_management.service.impl;


import com.inventory_management.dto.response.DashboardLowStockDTO;
import com.inventory_management.dto.response.DashboardRecentProductDTO;
import com.inventory_management.dto.response.DashboardResponseDTO;
import com.inventory_management.dto.response.DashboardSummaryDTO;

import com.inventory_management.repository.CategoryRepository;
import com.inventory_management.repository.ProductRepository;
import com.inventory_management.repository.SupplierRepository;
import com.inventory_management.repository.UserRepository;

import com.inventory_management.service.DashboardService;


import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;



import java.util.List;



@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {


    private final ProductRepository productRepository;


    private final CategoryRepository categoryRepository;


    private final SupplierRepository supplierRepository;


    private final UserRepository userRepository;




    @Override
    public DashboardResponseDTO getDashboard() {


        DashboardSummaryDTO summary = new DashboardSummaryDTO(

                productRepository.count(),

                categoryRepository.count(),

                supplierRepository.count(),

                userRepository.count(),

                productRepository.countByQuantityLessThanEqual(10)

        );



        List<DashboardRecentProductDTO> recentProducts =

                productRepository
                .findTop5ByOrderByCreatedAtDesc()
                .stream()
                .map(product -> new DashboardRecentProductDTO(

                        product.getProductId(),

                        product.getProductName(),

                        product.getCategory().getCategoryName(),

                        product.getQuantity(),

                        product.getSellingPrice(),

                        product.getCreatedAt()

                ))
                .toList();




        List<DashboardLowStockDTO> lowStockProducts =

                productRepository
                .findTop5ByQuantityLessThanEqualOrderByQuantityAsc(10)
                .stream()
                .map(product -> new DashboardLowStockDTO(

                        product.getProductId(),

                        product.getProductName(),

                        product.getQuantity(),

                        product.getCategory().getCategoryName()

                ))
                .toList();



        return new DashboardResponseDTO(

                summary,

                recentProducts,

                lowStockProducts

        );

    }


}