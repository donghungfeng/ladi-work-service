package com.example.ladiworkservice.model;

import com.example.ladiworkservice.dto.*;
import com.example.ladiworkservice.dto.*;
import lombok.*;

import javax.persistence.*;


@NamedNativeQuery(name = "Data.statisticcalrevenueByDay",
        query = "select date_only as date, sum(price) as price from data GROUP BY date_only ORDER BY date_only DESC LIMIT 50",
        resultSetMapping = "Mapping.StatisticalRevenueByDayDto")
@SqlResultSetMapping(name = "Mapping.StatisticalRevenueByDayDto", classes = @ConstructorResult(targetClass = StatisticalRevenueByDayDto.class
        , columns = {@ColumnResult(name = "date", type = Long.class),
        @ColumnResult(name = "price", type = Double.class)}))

@NamedNativeQuery(name = "Data.thongKeUtmTheoThoiGian_admin",
        query = "SELECT date_only as date, utm_medium as utmName,COUNT(id) as count  FROM `data` WHERE date_only <= :endDate AND date_only >= :startDate   GROUP BY utm_medium, date_only",
        resultSetMapping = "Mapping.KetQuaThongKeUtmDto")

@NamedNativeQuery(name = "Data.thongKeUtmTheoThoiGian",
        query = "SELECT date_only as date, utm_medium as utmName,COUNT(id) as count  FROM `data` WHERE date_only <= :endDate AND date_only >= :startDate AND utm_medium in (:list) GROUP BY utm_medium, date_only",
        resultSetMapping = "Mapping.KetQuaThongKeUtmDto")
@SqlResultSetMapping(name = "Mapping.KetQuaThongKeUtmDto", classes = @ConstructorResult(targetClass = KetQuaThongKeUtmDto.class,
        columns = {@ColumnResult(name = "date", type = Long.class),
                @ColumnResult(name = "utmName", type = String.class),
                @ColumnResult(name = "count", type = int.class)}))

@NamedNativeQuery(name = "Data.statisticalRevenueByDate",
        query = "SELECT date_only as date, SUM(price) as revenue  from `data` WHERE date_only >= :startDate AND date_only <= :endDate AND status in (7,8) AND shopcode = :codeShop GROUP BY date_only",
        resultSetMapping = "Mapping.StatisticalRevenueByDateDto")
@SqlResultSetMapping(name = "Mapping.StatisticalRevenueByDateDto", classes = @ConstructorResult(targetClass = StatisticalRevenueByDateDto.class,
        columns = {@ColumnResult(name = "date", type = Long.class),
                @ColumnResult(name = "revenue", type = Long.class)}))

@NamedNativeQuery(name = "Data.statisticUtmByDate",
        query = "SELECT COUNT(id) as count, date_only as date, utm_medium as utmMedium, SUM(price) as price From `data` WHERE date_only <= :endDate AND date_only >= :startDate AND shopcode = :shopCode And status in (7,8) GROUP By date_only, utm_medium  ",
        resultSetMapping = "Mapping.StatisticUtmByDateDto")
@SqlResultSetMapping(name = "Mapping.StatisticUtmByDateDto", classes = @ConstructorResult(targetClass = StatisticUtmByDateDto.class,
        columns = {@ColumnResult(name = "count", type = Integer.class),
                @ColumnResult(name = "date", type = Long.class),
                @ColumnResult(name = "utmMedium", type = String.class),
                @ColumnResult(name = "price", type = Long.class),
        }))
@NamedNativeQuery(name = "Data.statisticDataByDateAndStatus",
        query = "SELECT COUNT(id) as count, SUM(price) as sum, status  from `data` WHERE date_only <= :endDate and date_only >= :startDate AND shopcode = :shopCode GROUP BY status ",
        resultSetMapping = "Mapping.StatisticDataByDateAndStatusDto")

@NamedNativeQuery(name = "Data.statisticDataByDateAndStatusAndUser",
        query = "SELECT COUNT(id) as count, SUM(price) as sum, status  from `data` WHERE date_only <= :endDate and date_only >= :startDate AND shopcode = :shopCode And nhanvienid = :userId GROUP BY status ",
        resultSetMapping = "Mapping.StatisticDataByDateAndStatusDto")


@NamedNativeQuery(name = "Data.statisticQuantityDataByDateAndStatus",
        query = "SELECT COUNT(id) as count, status, SUM(status) as sum  from `data` WHERE date <= :endDate and date >= :startDate and shopcode = :shopCode GROUP BY status ",
        resultSetMapping = "Mapping.StatisticDataByDateAndStatusDto")

@SqlResultSetMapping(name = "Mapping.StatisticDataByDateAndStatusDto", classes = @ConstructorResult(targetClass = StatisticDataByDateAndStatusDto.class,
        columns = {@ColumnResult(name = "count", type = Integer.class),
                @ColumnResult(name = "sum", type = long.class),
                @ColumnResult(name = "status", type = Integer.class)}))

@NamedNativeQuery(name = "Data.statisticCostByStatusData",
        query = "SELECT SUM(cost) as total, SUM(cogs) as cogs,date_only as date, SUM(total_product_value) as totalProductValue FROM `data` " +
                "WHERE date_only <= :endDate AND date_only >= :startDate AND shopcode= :shopCode AND status IN (7,8) GROUP BY date_only",
        resultSetMapping = "Mapping.StatisticCostByStatusData")
@SqlResultSetMapping(name = "Mapping.StatisticCostByStatusData", classes = @ConstructorResult(targetClass = StatisticCostByStatusData.class,
        columns = {@ColumnResult(name = "total", type = long.class),
                @ColumnResult(name = "cogs", type = long.class),
                @ColumnResult(name = "date", type = long.class),
                @ColumnResult(name = "totalProductValue", type = long.class)
        }))


@Entity
@Table(name = "data")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Data {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "street")
    private String street;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Column(name = "product")
    private String product;

    @Column(name = "status")
    private int status;

    @Column(name = "date")
    private Long date;

    @Column(name = "date_only")
    private Long dateOnly;

    @Column(name = "date_changed_only")
    private Long dateChangedOnly;

    @Column(name = "link")
    private String link;

    @Column(name = "ipaddress")
    private String ipAddress;

    @Column(name = "datechanged")
    private Long dateChanged;

    @Column(name = "utm_source")
    private String utm_source;

    @Column(name = "utm_medium")
    private String utm_medium;

    @Column(name = "utm_campaign")
    private String utm_campation;

    @Column(name = "utm_term")
    private String utm_term;

    @Column(name = "utm_content")
    private String utm_content;

    @Column(name = "variant_url ")
    private String variant_url;

    @Column(name = "price")
    private Double price;

    @Column(name = "message")
    private String message;

    @Column(name = "total_product_value")
    private Long totalProductValue;

    @Column(name = "note")
    private String note;

    @Column(name = "shopcode")
    private String shopCode;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "COGS")
    private Long cogs;

    @Column(name = "product_ids")
    private String productIds;

    @Column(name = "delivery_fee")
    private Long deliveryFee;

    @Column(name = "discount")
    private Long discount;

    @Column(name = "reason")
    private String reason;

    @Column(name = "note_shippingGHSV")
    private String noteShipping;

    @ManyToOne
    @JoinColumn(name = "saleid")
    Account saleAccount;

    @ManyToOne
    @JoinColumn(name = "nhanvienid")
    Account account;

    @ManyToOne
    @JoinColumn(name = "productid")
    Product productDto;

    @ManyToOne
    @JoinColumn(name = "shipping_order_creator")
    Account shippingCreator;

    @ManyToOne
    @JoinColumn(name = "shipping_order_account")
    AccountShipping shippingAccount;

    @Column(name = "shipping_order_code")
    private String shippingCode;

    @Column(name = "time_order_success")
    private Long timeOrderSuccess;

    @Column(name = "data_info", length = 65000)
    private String dataInfo;

    @Column(name = "shipping_status")
    private String shippingStatus;

    @Column(name = "time_shipping")
    private Long timeShipping;

    @Column(name = "last_update_tracking_time")
    private Long lastUpdateTrackingTime;

    @Column(name = "time_financed")
    private Long timeFinanced;

    @OneToOne
    @JoinColumn(name = "bol")
    private BoL boL;

    @Column(name = "actual_fee")
    private Long actualFee;

    @ManyToOne
    @JoinColumn(name = "statistics_general")
    private StatisticsGeneral statisticsGeneral;
}
