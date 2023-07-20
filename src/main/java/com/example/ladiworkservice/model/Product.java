package com.example.ladiworkservice.model;

import com.example.ladiworkservice.dto.*;
import com.example.ladiworkservice.dto.ProductWarehouseDto;
import com.example.ladiworkservice.dto.RevenueInfoDashboard;
import com.example.ladiworkservice.dto.RevenueStatisticsDto;
import com.example.ladiworkservice.dto.SaleUtmDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@SqlResultSetMapping(name = "ProductWareHouseResult", classes = @ConstructorResult(targetClass = ProductWarehouseDto.class,
        columns = {
                @ColumnResult(name = "productAvaiable", type = Integer.class),
                @ColumnResult(name = "totalQuantity", type = Long.class),
        }))
@SqlResultSetMapping(name = "SaleUtmResult", classes = @ConstructorResult(targetClass = SaleUtmDto.class,
        columns = {
                @ColumnResult(name = "utmName", type = String.class),
                @ColumnResult(name = "count", type = Integer.class),
                @ColumnResult(name = "averagePrice", type = Integer.class)
//                @ColumnResult(name = "revenue", type = Long.class),
//                @ColumnResult(name = "sales", type = Long.class),
//                @ColumnResult(name = "Integer", type = Integer.class),
//                @ColumnResult(name = "orderAcceptRate", type = Double.class)
        }))
@SqlResultSetMapping(name = "RevenueStatisticsResult", classes = {
        @ConstructorResult(targetClass = RevenueStatisticsDto.class,
                columns = {
                        @ColumnResult(name = "date", type = Integer.class),
                        @ColumnResult(name = "averagePrice", type = Double.class),
                        @ColumnResult(name = "revenue", type = Long.class),
                        @ColumnResult(name = "totalOrder", type = Integer.class),
                        @ColumnResult(name = "averageOrder", type = Double.class),
                        @ColumnResult(name = "sales", type = Long.class),
                        @ColumnResult(name = "saleCostPrice", type = Long.class),
                })
})
@SqlResultSetMapping(name = "RevenueInfoDashboardResult", classes = {
        @ConstructorResult(targetClass = RevenueInfoDashboard.class,
                columns = {
                        @ColumnResult(name = "refundOrderRate", type = Double.class),
                        @ColumnResult(name = "revenue", type = Long.class),
                        @ColumnResult(name = "revenueAfterRefund", type = Double.class),
                        @ColumnResult(name = "sales", type = Long.class),
                        @ColumnResult(name = "averagePrice", type = Double.class),
                        @ColumnResult(name = "orderAcceptRate", type = Double.class),
                        @ColumnResult(name = "totalCostPriceAfterRefund", type = Long.class),
                        @ColumnResult(name = "totalCostPrice", type = Long.class),
                        @ColumnResult(name = "totalDeliveryFee", type = Double.class),
                        @ColumnResult(name = "totalDeliveryFeeAfterRefund", type = Integer.class),
                        @ColumnResult(name = "totalCustomer", type = Integer.class),
                        @ColumnResult(name = "totalOrder", type = Integer.class)
                })
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product extends BaseEntity{
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private int status = 1;

    @Column(name = "shopcode")
    private String shopcode;

    @Column(name = "note")
    private String note;

    @Column(name = "giaban")
    private Double giaBan;

    @Column(name = "gianhap")
    private Double giaNhap;

    @ManyToMany(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<OrderShipping> orders;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "properties")
    private String properties;

//    @Column(name = "category_id")
//    private Long categoryId;

    @Column(name = "create_at")
    private Long createAt;

    @Column(name = "update_at")
    private Long updateAt;

    @ManyToOne
    @JoinColumn(name = "shop", nullable = false)
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    private ProductCategory productCategory;

    @Column(name = "warehouse_id", nullable = false)
    private Long warehouseId;

}
