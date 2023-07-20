package com.example.ladiworkservice.model;

import com.example.ladiworkservice.dto.ProductSoldDashboardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@SqlResultSetMapping(name = "ProductSoldResult", classes = {
        @ConstructorResult(targetClass = ProductSoldDashboardDto.class, columns = {
                @ColumnResult(name = "totalProductSolded", type = Long.class),
        })
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sub_product")
public class SubProduct extends BaseEntity {
    // @Column(name = "warehouse_id")
    // private Long warehouseId;
    @ManyToOne
    @JoinColumn(name = "warehouse", nullable = false)
    private Warehouse warehouse;

    // @Column(name = "product_id")
    // private Long productId;

    @ManyToOne
    @JoinColumn(name = "product", nullable = false)
    private Product product;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "properties")
    private String properties;

    @Column(name = "last_imported_price")
    private Long lastImportedPrice;

    @Column(name = "price")
    private Long price = 0L;

    @Column(name = "status")
    private int status = 1;

    @Column(name = "wide")
    private Double wide = 0.0;

    @Column(name = "length")
    private Double length = 0.0;

    @Column(name = "high")
    private Double high = 0.0;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "sold")
    private Long sold = 0L;

    @Column(name = "total_quantity")
    private Long totalQuantity;

    @Column(name = "available_quantity")
    private Long availableQuantity;

    @Column(name = "defective_product_quantity")
    private Long defectiveProductQuantity;

    @Column(name = "inventory_quantity")
    private Long inventoryQuantity;

    @Column(name = "awaiting_product_quantity")
    private Long awaitingProductQuantity;

    @Column(name = "note")
    private String note;

    @Column(name = "create_at")
    private Long createAt;

    @Column(name = "update_at")
    private Long updateAt;
}
