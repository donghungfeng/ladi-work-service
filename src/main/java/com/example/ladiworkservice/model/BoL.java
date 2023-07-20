package com.example.ladiworkservice.model;

import com.example.ladiworkservice.controller.reponse.StatisticBoLRespone;
import lombok.*;

import javax.persistence.*;
import java.util.List;
@NamedNativeQuery(name = "BoL.statisticBoL",
        query = "SELECT SUM(temp.inventory_quantity) as totalInventoryQuantity, SUM(temp.total_quantity) as totalProduct, SUM(temp.inventory_quantity * temp.last_imported_price) as totalPrice " +
                "FROM " +
                "(SELECT DISTINCT sp.id, sp.inventory_quantity, sp.total_quantity,sp.last_imported_price  " +
                "FROM sub_product sp , bol b , bol_detail bd WHERE sp.id = bd.sub_product and bd.bol = b.id and b.`type` = :type and b.shop = :shopCode) as temp",
        resultSetMapping = "Mapping.StatisticBoLRespone")
@SqlResultSetMapping(name = "Mapping.StatisticBoLRespone", classes = @ConstructorResult(targetClass = StatisticBoLRespone.class,
        columns = {@ColumnResult(name = "totalInventoryQuantity", type = long.class),
                @ColumnResult(name = "totalProduct", type = long.class),
                @ColumnResult(name = "totalPrice", type = long.class)
        }))

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "BoL")
public class BoL extends BaseEntity{
//    @Column(name = "created_id")
//    private Long createdId;

    @ManyToOne
    @JoinColumn(name = "creator")
    private Account creator;

//    @ManyToOne
//    @JoinColumn(name = "creator")
//    private Account account;

    @Column(name = "supplier_id")
    private String supplierInfo;

    @Column(name = "type")
    private int type;

    @Column(name = "status")
    private int status;

//    @Column(name = "warehouse_id")
//    private Long warehouseId;

    @ManyToOne
    @JoinColumn(name = "warehouse")
    private Warehouse warehouse;

    @Column(name = "create_at")
    private Long createAt;

    @Column(name = "update_at")
    private Long updateAt;

    @Column(name = "tciq")
    private Long totalImport;

    @Column(name = "estimated_return_date")
    private Long estimatedReturnDate;

    @Column(name = "total_price")
    private Long totalPrice;

    @OneToMany(mappedBy = "boL")
    private List<BoLDetail> boLDetailList;

    @ManyToOne
    @JoinColumn(name = "shop", nullable = false)
    private Shop shop;

    @Column(name = "note")
    private String note;
}
