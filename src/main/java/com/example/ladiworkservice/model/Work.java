package com.example.ladiworkservice.model;
import com.example.ladiworkservice.dto.StatisticPerformanceSaleDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NamedNativeQuery(name = "Work.statisticPerformanceSale",
        query = "SELECT a.fullname as fullName, SUM(w.total_order) as totalOrder ,SUM(w.success_order) as successOrder , SUM(w.processed_order) as processedOrder, SUM(w.only_order) as onlyOrder, SUM(w.processed_only_order) as processedOnlyOrder FROM `work` w INNER JOIN account a ON w.nhanvienid = a.id WHERE w.timeint >= :startDate AND w.timeout <= :endDate AND shopcode = :shopCode GROUP BY a.fullname ",
        resultSetMapping = "Mapping.StatisticPerformanceSaleDto")
@SqlResultSetMapping(name = "Mapping.StatisticPerformanceSaleDto", classes = @ConstructorResult(targetClass = StatisticPerformanceSaleDto.class,
        columns = {@ColumnResult(name = "fullName", type = String.class),
                @ColumnResult(name = "totalOrder", type = Integer.class),
                @ColumnResult(name = "successOrder", type = Integer.class),
                @ColumnResult(name = "processedOrder", type = Integer.class),
                @ColumnResult(name = "onlyOrder", type = Integer.class),
                @ColumnResult(name = "processedOnlyOrder", type = Integer.class)}))

@Entity
@Table(name = "work")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Work extends BaseEntity{

    @Column(name = "timeint")
    private Long timeIn;

    @Column(name = "timeout")
    private Long timeOut;

    @Column(name = "total_order")
    private int totalOrder = 0;

    @Column(name = "success_order")
    private int successOrder = 0;

    @Column(name = "processed_order")
    private int processedOrder = 0;

    @Column(name = "only_order")
    private int onlyOrder = 0;

    @Column(name = "processed_only_order")
    private int processedOnlyOrder = 0;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "active")
    private int isActive;

    @Column(name = "shopcode")
    private String shopCode;

    @ManyToOne
    @JoinColumn(name = "nhanvienid", nullable = false)
    Account account;
}
