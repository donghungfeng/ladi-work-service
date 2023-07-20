package com.example.ladiworkservice.model;

import com.example.ladiworkservice.dto.MvpSaleKpiStatisticDTO;
import com.example.ladiworkservice.dto.SalerKpiStatisticDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
@NamedNativeQuery(name = "SalePerformance.saleKpiStatistics",
        query = "SELECT a.fullname AS salerName,\n" +
                "       COALESCE(SUM(CASE WHEN sks.shop_id = :shopId THEN sks.total_order_assigned ELSE 0 END), 0) AS totalOrderAssigned, \n" +
                "       COALESCE(SUM(CASE WHEN sks.shop_id = :shopId THEN sks.total_order_processed ELSE 0 END), 0) AS totalOrderProcessed, \n" +
                "       CASE\n" +
                "           WHEN SUM(CASE WHEN sks.shop_id = :shopId THEN sks.total_order_assigned ELSE 0 END) = 0 THEN 0\n" +
                "           ELSE COALESCE(ROUND(SUM(CASE WHEN sks.shop_id = :shopId THEN sks.total_order_processed ELSE 0 END) * 100 / SUM(CASE WHEN sks.shop_id = :shopId THEN sks.total_order_assigned ELSE 0 END), 2), 0)\n" +
                "       END AS orderProcessingRate,\n" +
                "       COALESCE(SUM(CASE WHEN sks.shop_id = :shopId THEN sks.total_order_success ELSE 0 END), 0) AS totalOrderSuccess,\n" +
                "       CASE\n" +
                "           WHEN SUM(CASE WHEN sks.shop_id = :shopId THEN sks.total_order_processed ELSE 0 END) = 0 THEN 0\n" +
                "           ELSE COALESCE(ROUND(SUM(CASE WHEN sks.shop_id = :shopId THEN sks.total_order_success ELSE 0 END) * 100 / SUM(CASE WHEN sks.shop_id = 10 THEN sks.total_order_processed ELSE 0 END), 2), 0)\n" +
                "       END AS orderSuccessRate,\n" +
                "       COALESCE(SUM(CASE WHEN sks.shop_id = :shopId THEN sks.sales ELSE 0 END), 0) AS sales,\n" +
                "       COALESCE(SUM(CASE WHEN sks.shop_id = :shopId THEN sks.revenue ELSE 0 END), 0) AS revenue,\n" +
                "       COALESCE(SUM(CASE WHEN sks.shop_id = :shopId THEN sks.actual_revenue ELSE 0 END), 0) AS actualRevenue,\n" +
                "       CASE\n" +
                "           WHEN SUM(CASE WHEN sks.shop_id = :shopId THEN sks.total_order_success ELSE 0 END) = 0 THEN 0\n" +
                "           ELSE COALESCE(ROUND(SUM(CASE WHEN sks.shop_id = :shopId THEN sks.sales ELSE 0 END) / SUM(CASE WHEN sks.shop_id = :shopId THEN sks.total_order_success ELSE 0 END), 0), 0)\n" +
                "       END AS averageOrderValue,\n" +
                "       CASE\n" +
                "           WHEN SUM(CASE WHEN sks.shop_id = :shopId THEN sks.total_order_assigned ELSE 0 END) = 0 THEN 0\n" +
                "           ELSE COALESCE(ROUND(SUM(CASE WHEN sks.shop_id = :shopId THEN sks.total_order_success ELSE 0 END) * 100 / SUM(CASE WHEN sks.shop_id = :shopId THEN sks.total_order_assigned ELSE 0 END), 2), 0)\n" +
                "       END AS closeRate\n" +
                "FROM account a\n" +
                "LEFT OUTER JOIN saler_kpi_statistics sks ON a.id = sks.saleid AND sks.date BETWEEN :startDate AND :endDate\n" +
                "WHERE a.role = 'user' AND a.shop LIKE CONCAT('%', :shopCode , '%') \n" +
                "GROUP BY a.id,a.fullname ",
        resultSetMapping = "Mapping.SalerKpiStatisticDto")
@SqlResultSetMapping(name = "Mapping.SalerKpiStatisticDto", classes = @ConstructorResult(targetClass = SalerKpiStatisticDto.class,
        columns = {
                @ColumnResult(name = "salerName", type = String.class),
                @ColumnResult(name = "totalOrderAssigned", type = Long.class),
                @ColumnResult(name = "totalOrderProcessed", type = Long.class),
                @ColumnResult(name = "orderProcessingRate", type = Float.class),
                @ColumnResult(name = "totalOrderSuccess", type = Long.class),
                @ColumnResult(name = "orderSuccessRate", type = Float.class),
                @ColumnResult(name = "sales", type = Double.class),
                @ColumnResult(name = "revenue", type = Double.class),
                @ColumnResult(name = "actualRevenue", type = Double.class),
                @ColumnResult(name = "averageOrderValue", type = Double.class),
                @ColumnResult(name = "closeRate", type = Float.class)
        }))
@NamedNativeQuery(name = "SalePerformance.mvpSaleKpiStatistics",
        query = "SELECT a.fullname AS salerName, IFNULL(SUM(d.price), 0) AS totalSales\n" +
                "FROM account a\n" +
                "LEFT JOIN `data` d ON d.saleid = a.id\n" +
                "AND MONTH(d.date_only) = MONTH(CURRENT_DATE())\n" +
                "AND YEAR(d.date_only) = YEAR(CURRENT_DATE())\n" +
                "AND d.status IN (7, 8, 9, 10, 11, 12)\n" +
                "AND d.shopcode = :shopCode \n" +
                "WHERE a.`role` = 'user' \n" +
                "AND  a.shop LIKE CONCAT('%', :shopCode , '%')\n" +
                "GROUP BY a.id, a.fullname\n" +
                "ORDER BY totalSales DESC;\n",
        resultSetMapping = "Mapping.MvpSaleKpiStatisticDTO")
@SqlResultSetMapping(name = "Mapping.MvpSaleKpiStatisticDTO", classes = @ConstructorResult(targetClass = MvpSaleKpiStatisticDTO.class,
        columns = {
                @ColumnResult(name = "salerName", type = String.class),
                @ColumnResult(name = "totalSales", type = Double.class),
        }))
@Entity
@Table(name = "saler_kpi_statistics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SalerKpiStatistics {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date")
    @JsonFormat(pattern="yyyyMMdd")
    private Long date;
    @ManyToOne
    @JoinColumn(name = "saleid")
    private Account saler;
    @ManyToOne
    @JoinColumn(name = "shopId")
    private Shop shop;
    @Column(name = "total_order_assigned")
    private Long totalOrderAssigned;
    @Column(name = "total_order_processed")
    private Long totalOrderProcessed;
    @Column(name = "order_processing_rate")
    private Float orderProcessingRate;
    @Column(name = "total_order_success")
    private Long totalOrderSuccess;
    @Column(name = "order_success_rate")
    private Float orderSuccessRate;
    @Column(name = "sales")
    private Double sales;
    @Column(name = "revenue")
    private Double Revenue;
    @Column(name = "actual_revenue")
    private Double actualRevenue;
    @Column(name = "average_order_value")
    private Double averageOrderValue;
    @Column(name = "close_rate")
    private Float closeRate;


}
