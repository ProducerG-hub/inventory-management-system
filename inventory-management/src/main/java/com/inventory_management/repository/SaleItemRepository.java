package com.inventory_management.repository;

import com.inventory_management.dto.response.ProfitReportDTO;

import com.inventory_management.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Integer> {

    @Query("""
    SELECT COALESCE(
    SUM(
    (si.unitPrice - si.costPrice) * si.quantity
    ),
    0
    )
    FROM SaleItem si
    """)
        BigDecimal getTotalProfit();

    @Query("""
SELECT new com.inventory_management.dto.response.ProfitReportDTO(

    p.productId,

    p.productName,

    SUM(si.quantity),

    SUM(si.unitPrice * si.quantity),

    SUM(si.costPrice * si.quantity),

    SUM((si.unitPrice - si.costPrice) * si.quantity)

)

FROM SaleItem si

JOIN si.product p

GROUP BY
    p.productId,
    p.productName

ORDER BY
    SUM((si.unitPrice - si.costPrice) * si.quantity) DESC

""")
    List<ProfitReportDTO> getProfitReport();

    @Query(
            value = """
        SELECT

            p.product_id,

            p.product_name,

            SUM(si.quantity) AS total_quantity,

            SUM(si.subtotal) AS total_revenue,

            COUNT(DISTINCT si.sale_id) AS total_orders


        FROM sale_items si


        INNER JOIN products p

        ON si.product_id = p.product_id


        GROUP BY

            p.product_id,

            p.product_name


        ORDER BY

            total_quantity DESC


        LIMIT 5

        """,
            nativeQuery = true
    )
    List<Object[]> getTopSellingProducts();
}
