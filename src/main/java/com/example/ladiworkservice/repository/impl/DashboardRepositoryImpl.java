package com.example.ladiworkservice.repository.impl;

import com.example.ladiworkservice.dto.*;
import com.example.ladiworkservice.dto.*;
import com.example.ladiworkservice.repository.DashboardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DashboardRepositoryImpl implements DashboardRepository {

    private final EntityManager entityManager;
    Logger logger = LoggerFactory.getLogger(DashboardRepositoryImpl.class);

    public DashboardRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public RevenueInfoDashboard getDataRevenueInfoDashboard(String shopCode, Long fromDate, Long toDate) {
        RevenueInfoDashboard result;
        String query = String.format("SELECT " +
                " IFNULL(ROUND((SUM(CASE WHEN d.status = 10 THEN 1 ELSE 0 END)) / (SUM(CASE WHEN d.status IN(7,8,10) THEN 1 ELSE 0 END)), 3), 0)  AS refundOrderRate,  "
                +
                " IFNULL( ROUND(SUM(CASE WHEN d.status IN (7,8) THEN d.price ELSE 0 END), 2), 1) as revenue,  "
                +
                " IFNULL(COUNT(distinct CASE WHEN d.status IN (7,8) THEN d.phone ELSE 0 END), 0) as totalCustomer, "
                +
                " IFNULL( ROUND((SUM(CASE WHEN d.status IN (7,8) THEN d.price ELSE 0 END) * (1 - ((SUM(CASE WHEN d.status = 10 THEN 1 ELSE 0 END)) / SUM(CASE WHEN d.status IN (7,8)THEN 1 ELSE 0 END)))), 2), 0) as revenueAfterRefund,  "
                +
                " IFNULL( (SUM(CASE WHEN d.status IN (7,8) THEN d.price ELSE 0 END) - SUM(CASE WHEN d.status = 10 THEN d.price ELSE 0 END)), 0) as sales, "
                +
                " IFNULL(ROUND((SUM(CASE WHEN d.status IN (7,8) THEN d.price ELSE 0 END)/ SUM(CASE WHEN d.status IN (7,8) THEN 1 ELSE 0 END)), 0), 0) as averagePrice ,"
                +
                " IFNULL( (SUM(CASE WHEN d.status IN (7,8) THEN d.total_product_value ELSE 0 END) - SUM(CASE WHEN d.status = 10 THEN d.total_product_value ELSE 0 END)), 0) as totalCostPriceAfterRefund, "
                +
                " IFNULL( SUM(CASE WHEN d.status IN (7,8) THEN d.total_product_value ELSE 0 END), 0) as totalCostPrice,"
                +
                " IFNULL( SUM(CASE WHEN d.status IN (7,8) THEN d.cost ELSE 0 END), 0) as totalDeliveryFee," +
                " IFNULL((SUM(CASE WHEN d.status IN (7,8) THEN d.cost ELSE 0 END) - SUM(CASE WHEN d.status = 10 THEN (d.cogs + d.cost) ELSE 0 END)), 0) as totalDeliveryFeeAfterRefund,"
                +
                " IFNULL( SUM(CASE WHEN d.status IN (7,8) THEN 1 ELSE 0 END), 0) as totalOrder," +
                " IFNULL(ROUND(((SUM(CASE WHEN d.status IN (7,8) THEN 1 ELSE 0 END)) / SUM(CASE WHEN d.status IN (1,2,3,4,5,6,7,8,9,10,11) THEN 1  ELSE 0 END))*100, 2), 0)  AS orderAcceptRate    "
                +
                " FROM data as d"
                +
                " WHERE d.shopcode = '%s' AND d.date_only >= %s AND d.date_only <= %s;", shopCode, fromDate, toDate);

        try {
            result = (RevenueInfoDashboard) entityManager
                    .createNativeQuery(query, "RevenueInfoDashboardResult")
                    .getSingleResult();
        } catch (Exception ex) {
            logger.error(
                    String.format("DashboardRepositoryImpl|getDataRevenueInfoDashboard|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return result;
    }

    public CostDataDashBoardDto getCostDataDashboard(String shopCode, Long fromDate, Long toDate) {
        CostDataDashBoardDto result;
        String query = String.format("SELECT " +
                " IFNULL( SUM(CASE WHEN c.type_id = 7 THEN c.total_cost ELSE 0 END), 0) AS totalMarketingCost,"
                +
                " IFNULL( SUM(CASE WHEN c.type_id IN (9,10) THEN c.total_cost ELSE 0 END), 0) AS totalOperationCost,"
                +
                " IFNULL( SUM(CASE WHEN c.type_id IN (6, 7, 9, 10) THEN c.total_cost ELSE 0 END), 0)  AS totalCost, "
                +
                " IFNULL( SUM(CASE WHEN c.type_id IN (6) THEN c.total_cost ELSE 0 END), 0)  AS otherCost"
                +
                " FROM cost c " +
                " WHERE c.shopcode = '%s' AND c.from_date >= %s AND c.to_date  <= %s;", shopCode, fromDate, toDate);

        try {
            result = (CostDataDashBoardDto) entityManager
                    .createNativeQuery(query, "CostDashboardResult")
                    .getSingleResult();
        } catch (Exception ex) {
            logger.error(String.format("DashboardRepositoryImpl|getCostDataDashboard|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return result;
    }

    public ProductSoldDashboardDto getProductSoldDashboard(String shopCode, Long fromDate, Long toDate) {
        ProductSoldDashboardDto result;

        String query = String.format("SELECT " +
                " IFNULL( ISNULL(SUM(sp.sold)), 0) as totalProductSolded" +
                " FROM sub_product as sp" +
                " WHERE  sp.create_at >= %s and sp.create_at <= %s", fromDate, toDate);
        try {
            result = (ProductSoldDashboardDto) entityManager
                    .createNativeQuery(query, "ProductSoldResult")
                    .getSingleResult();
        } catch (Exception ex) {
            logger.error(
                    String.format("DashboardRepositoryImpl|getProductSoldDashboardDto|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return result;
    }

    public List<RevenueStatisticsDto> getRevenueStatisticsData(String shopCode, Long fromDate, Long toDate) {
        List<RevenueStatisticsDto> result = new ArrayList<RevenueStatisticsDto>();

        String query = String.format("SELECT d.date_only as date," +
                " IFNULL(ROUND(AVG(CASE WHEN d.status IN (7,8) THEN d.price ELSE 0 END), 2), 0) as averagePrice," +
                " IFNULL(ROUND(SUM(CASE WHEN d.status IN (7,8) THEN d.price ELSE 0 END), 2), 0) as revenue," +
                " IFNULL(SUM(CASE WHEN d.status IN (7,8) THEN 1 ELSE 0 END), 0) as totalOrder," +
                " IFNULL(SUM(d.id), 0) as averageOrder," +
                " IFNULL((SUM(CASE WHEN d.status IN (7,8) THEN d.price ELSE 1 END) - SUM(CASE WHEN d.status = 10 THEN d.price ELSE 1 END)), 0) as sales ,"
                +
                " IFNULL((SUM(CASE WHEN d.status IN (7,8) THEN d.price ELSE 1 END) - SUM(CASE WHEN d.status = 10 THEN d.price ELSE 1 END) - SUM(CASE WHEN d.status IN (7,8) THEN d.total_product_value ELSE 0 END)), 0) as saleCostPrice, "
                +
                " ROUND((AVG(CASE WHEN d.status IN (7,8) THEN d.price ELSE 1 END) - AVG(CASE WHEN d.status = 10 THEN d.price ELSE 1 END) - AVG(CASE WHEN d.status IN (7,8) THEN d.total_product_value ELSE 0 END)),0) as averageSaleCostPrice "
                +
                " FROM data as d " +
                " WHERE d.shopcode = '%s' AND d.date_only >= %s AND d.date_only <= %s" +
                " GROUP BY d.date_only;", shopCode, fromDate, toDate);

        try {
            List<Object[]> results = entityManager
                    .createNativeQuery(query)
                    .getResultList();

            for (Object[] object : results) {
                RevenueStatisticsDto revenueStatisticsDto = new RevenueStatisticsDto();
                revenueStatisticsDto.setDate((BigInteger) object[0]);
                revenueStatisticsDto.setAveragePrice((Double) object[1]);
                revenueStatisticsDto.setTotalOrder((BigDecimal) object[3]);
                revenueStatisticsDto.setAverageOrder((BigDecimal) object[4]);
                revenueStatisticsDto.setSales((Double) object[5]);
                revenueStatisticsDto.setSaleCostPrice((Double) object[6]);
                revenueStatisticsDto.setAverageSaleCostPrice((Double) object[7]);
                result.add(revenueStatisticsDto);
            }
        } catch (Exception ex) {
            logger.error(String.format("DashboardRepositoryImpl|getRevenueStatisticsData|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return result;
    }

    public ProductWarehouseDto getProductWarehouseDto(String shopCode, Long fromDate, Long toDate) {
        ProductWarehouseDto result;

        String query = String.format("SELECT SUM(sp.available_quantity) AS productAvaiable," +
                " SUM(sp.available_quantity * sp.price) AS totalQuantity " +
                " FROM laditest.sub_product sp " +
                " JOIN laditest.product p " +
                " ON sp.product = p.id " +
                " WHERE p.shopcode = '%s';", shopCode);
        ;

        try {
            result = (ProductWarehouseDto) entityManager
                    .createNativeQuery(query, "ProductWareHouseResult")
                    .getSingleResult();
        } catch (Exception ex) {
            logger.error(String.format("DashboardRepositoryImpl|getProductWarehouseDto|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return result;
    }

    @Override
    public List<ProductWareHouseChartDto> getProductWareHouseChartDto(Long fromDate, Long toDate) {
        List<ProductWareHouseChartDto> result = new ArrayList<>();

        String query = String.format("SELECT " +
                " sp.update_at as date, " +
                "   IFNULL(SUM(sp.available_quantity), 0) as productAvaiable, " +
                "    IFNULL(AVG(sp.available_quantity), 0) as averageProduct " +
                "    FROM warehouse w " +
                "    INNER JOIN sub_product sp ON w.id = sp.warehouse " +
                " WHERE sp.update_at >= 20230501 AND sp.update_at <= 20230531 " +
                " GROUP BY sp.update_at ", fromDate, toDate);

        try {
            List<Object[]> results = entityManager
                    .createNativeQuery(query)
                    .getResultList();

            for (Object[] object : results) {
                ProductWareHouseChartDto product = new ProductWareHouseChartDto();
                product.setDate((BigInteger) object[0]);
                product.setProductAvaiable((BigDecimal) object[1]);
                product.setAverageProduct((BigDecimal) object[2]);
                result.add(product);
            }
        } catch (Exception ex) {
            logger.error(
                    String.format("DashboardRepositoryImpl|getProductWareHouseChartDto|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return result;
    }

    public List<CostDataDto> getCostData(String shopCode, Long fromDate, Long toDate) {
        List<CostDataDto> result = new ArrayList<>();

        String query = String.format("SELECT " +
                " c.from_date as `date`," +
                " IFNULL(ROUND(AVG(CASE WHEN c.type_id IN (6,7,8,9,10) THEN c.total_cost ELSE 0 END), 0), 0)  AS averageCost,"
                +
                " SUM(CASE WHEN c.type_id IN (6,7,8,9,10) THEN c.total_cost ELSE 0 END)  AS totalCost " +
                " FROM cost c  " +
                " WHERE c.shopcode = 'KHBOM' AND c.from_date >= 20230401 AND c.to_date  <= 20230530" +
                " GROUP BY c.from_date;", shopCode, fromDate, toDate);

        try {
            List<Object[]> results = entityManager
                    .createNativeQuery(query)
                    .getResultList();

            for (Object[] object : results) {
                CostDataDto cost = new CostDataDto();
                cost.setDate((BigInteger) object[0]);
                cost.setAverageCost((BigDecimal) object[1]);
                cost.setTotalCost((BigDecimal) object[2]);
                result.add(cost);
            }
        } catch (Exception ex) {
            logger.error(String.format("DashboardRepositoryImpl|getCostData|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return result;
    }

    public List<SaleUtmDto> getSaleUtmDto(String shopCode, Long fromDate, Long toDate) {
        List<SaleUtmDto> result = new ArrayList<>();

        String query = String.format("SELECT a.fullname as utmName, " +
                " COUNT(*) as count ," +
                " IFNULL(ROUND(AVG(CASE WHEN d.status IN (7,8) THEN d.price ELSE 0 END), 0), 0) as averagePrice, " +
                " IFNULL(ROUND(SUM(CASE WHEN d.status IN (7,8) THEN d.price ELSE 0 END), 2), 0) as revenue, " +
                " IFNULL((SUM(CASE WHEN d.status IN (7,8) THEN d.price ELSE 1 END) - SUM(CASE WHEN d.status = 10 THEN d.price ELSE 1 END)), 0) as sales , "
                +
                " IFNULL(SUM(CASE WHEN d.status IN (7,8) THEN 1 ELSE 0 END), 0) as totalOrder, " +
                " IFNULL( ROUND(((SUM(CASE WHEN d.status IN (7,8) THEN 1 ELSE 0 END)) / SUM(CASE WHEN d.status IN (1,2,3,4,5,6,7,8,9,10,11) THEN 1  ELSE 1 END))*100,2), 0)  AS orderAcceptRate"
                +
                " From `data` d,account a  " +
                " WHERE " +
                " d.nhanvienid = a.id " +
                " AND date_only <= %s AND  date_only >= %s AND d.shopcode = '%s' " +
                " GROUP BY a.username " +
                " ORDER BY count DESC", toDate, fromDate, shopCode);

        try {
            List<Object[]> results = entityManager
                    .createNativeQuery(query)
                    .getResultList();

            for (Object[] object : results) {
                SaleUtmDto saleUtm = new SaleUtmDto();
                saleUtm.setUtmMedium((String) object[0]);
                saleUtm.setCount((BigInteger) object[1]);
                saleUtm.setAveragePrice((Double) object[2]);
                saleUtm.setRevenue((Double) object[3]);
                saleUtm.setSales((Double) object[4]);
                saleUtm.setTotalOrder((BigDecimal) object[5]);
                saleUtm.setOrderAcceptRate((BigDecimal) object[6]);
                result.add(saleUtm);
            }
        } catch (Exception ex) {
            logger.error(String.format("DashboardRepositoryImpl|getSaleUtmDto|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return result;
    }

    public List<MarketingUtmDto> getMarketingUtmDto(String shopCode, Long fromDate, Long toDate) {
        List<MarketingUtmDto> result = new ArrayList<>();

        String query = String.format("SELECT a.fullname as utmName, " +
                " COUNT(*) as count ," +
                " IFNULL(SUM(CASE WHEN d.status IN (7,8) THEN 1 ELSE 0 END), 0) as totalOrder, " +
                " IFNULL( ROUND(((SUM(CASE WHEN d.status IN (7,8) THEN 1 ELSE 0 END)) / SUM(CASE WHEN d.status IN (1,2,3,4,5,6,7,8,9,10,11) THEN 1  ELSE 1 END))*100,2), 0)  AS orderAcceptRate "
                +
                " From `data` d,utm_medium um,account a  " +
                " WHERE d.utm_medium = um.code " +
                " AND um.nhanvienid = a.id " +
                " AND date_only <= %s AND  date_only >= %s AND d.shopcode = '%s' " +
                " GROUP BY a.username " +
                " ORDER BY count DESC", toDate, fromDate, shopCode);

        try {
            List<Object[]> results = entityManager
                    .createNativeQuery(query)
                    .getResultList();

            for (Object[] object : results) {
                MarketingUtmDto marketingUtm = new MarketingUtmDto();
                marketingUtm.setUtmMedium((String) object[0]);
                marketingUtm.setCount((BigInteger) object[1]);
                marketingUtm.setTotalOrder((BigDecimal) object[2]);
                marketingUtm.setOrderAcceptRate((BigDecimal) object[3]);
                result.add(marketingUtm);
            }
        } catch (Exception ex) {
            logger.error(String.format("DashboardRepositoryImpl|getMarketingUtmDto|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return result;
    }

    public List<CostMarketingGroupByName> getCostMarketingGroupByName(String shopCode, Long fromDate, Long toDate) {
        List<CostMarketingGroupByName> result = new ArrayList<>();

        String query = String.format("SELECT " +
                " c.name, " +
                " IFNULL(ROUND(AVG(CASE WHEN c.type_id IN (7) THEN c.total_cost ELSE 0 END), 0), 0)  AS costMarketingByName "
                +
                " FROM cost c " +
                " WHERE c.shopcode = '%s' AND c.from_date >= %s AND c.to_date  <= %s " +
                " GROUP BY c.name;", shopCode, fromDate, toDate);

        try {
            List<Object[]> results = entityManager
                    .createNativeQuery(query)
                    .getResultList();

            for (Object[] object : results) {
                CostMarketingGroupByName costMarketing = new CostMarketingGroupByName();
                costMarketing.setName((String) object[0]);
                costMarketing.setCostMarketingByName((BigDecimal) object[1]);
                result.add(costMarketing);
            }
        } catch (Exception ex) {
            logger.error(
                    String.format("DashboardRepositoryImpl|getCostMarketingGroupByName|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return result;
    }

}
