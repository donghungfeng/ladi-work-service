package com.example.ladiworkservice.repository;




import com.example.ladiworkservice.dto.MvpSaleKpiStatisticDTO;
import com.example.ladiworkservice.dto.SalerKpiStatisticDto;
import com.example.ladiworkservice.model.Account;
import com.example.ladiworkservice.model.SalerKpiStatistics;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SaleKpiStatisticRepository extends BaseRepository<SalerKpiStatistics>{
//    @Query(value = "SELECT * FROM saler_kpi_statistics sks WHERE sks.date=:date and sks.saleid =:saleID ",nativeQuery = true)
    SalerKpiStatistics findAllByDateAndSaler(Long date, Account saler);
    @Transactional
    @Modifying
    @Query(value = "UPDATE saler_kpi_statistics\n" +
            "SET\n" +
            "    total_order_assigned = COALESCE(total_order_assigned, 0) + COALESCE(:totalOrderAssigned , 0),\n" +
            "    total_order_processed = COALESCE(total_order_processed, 0) + COALESCE(:totalOrderProcessed , 0),\n" +
            "    total_order_success = COALESCE(total_order_success, 0) + COALESCE(:totalOrderSuccess, 0),\n" +
            "    order_processing_rate = CASE\n" +
            "                                WHEN total_order_assigned = 0 THEN 0\n" +
            "                                ELSE COALESCE(ROUND((total_order_processed) / (total_order_assigned)*100, 2), 0)\n" +
            "                            END,\n" +
            "    order_success_rate = CASE\n" +
            "                            WHEN total_order_processed = 0 THEN 0\n" +
            "                            ELSE COALESCE(ROUND(total_order_success / (total_order_processed)* 100, 2), 0)\n" +
            "                        END,\n" +
            "    sales = COALESCE(sales, 0) + COALESCE(:sales , 0),\n" +
            "    actual_revenue = COALESCE(actual_revenue, 0) + COALESCE(:actualRevenue , 0),\n" +
            "    revenue = COALESCE(revenue, 0) + COALESCE(:revenue , 0),\n" +
            "    average_order_value = CASE\n" +
            "                             WHEN total_order_success = 0 THEN 0\n" +
            "                             ELSE COALESCE(ROUND(sales / total_order_success, 0), 0)\n" +
            "                          END,\n" +
            "    close_rate = CASE\n" +
            "                    WHEN total_order_assigned = 0 THEN 0\n" +
            "                    ELSE COALESCE(ROUND(total_order_success * 100 / (total_order_assigned), 2), 0)\n" +
            "                END,\n" +
            "    shop_id = :shopId \n" +
            "WHERE date = :date AND saleid = :saleID ", nativeQuery = true)

    void UpdateSalerKpiStatistics(Long date,Long saleID, Long totalOrderAssigned, Long totalOrderProcessed, Long totalOrderSuccess,
                                                Double sales,Double actualRevenue,Double revenue, Long shopId);

    @Query(name="SalePerformance.saleKpiStatistics",nativeQuery = true)
    List<SalerKpiStatisticDto> getSalerKpiStatistic(Long startDate, Long endDate, Long shopId, String shopCode);

    @Query(name="SalePerformance.mvpSaleKpiStatistics",nativeQuery = true)
    List<MvpSaleKpiStatisticDTO> getMvpSalerKpiStatistic(String shopCode);
    @Query (value = "Select * from saler_kpi_statistics sks where sks.saleid =:saleId and sks.date=:date ",nativeQuery = true)
    SalerKpiStatistics findSalerKpiStatisticsBySaleIdAndDateAndShop(Long saleId,Long date);

    @Query (value = "select * from saler_kpi_statistics where saleid = :saleId and date = :date", nativeQuery = true)
    SalerKpiStatistics findAllBySaleIdAndDate(Long saleId, Long date);
}
