package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.controller.reponse.StatisticGeneralResponse;
import com.example.ladiworkservice.controller.reponse.StatisticsRevenueResponse;
import com.example.ladiworkservice.dto.*;
import com.example.ladiworkservice.dto.*;
import com.example.ladiworkservice.model.Account;
import com.example.ladiworkservice.model.Data;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DataRepository extends BaseRepository<Data> {


    List<Data> findAll();

    Data findAllById(Long id);

    List<Data> findAllByAccountIsNull();

    @Query(
            value = "SELECT * FROM data as d WHERE (:status is null or d.status = :status) AND (d.date BETWEEN :startDate AND :endDate)",
            nativeQuery = true)
    List<Data> findAllByStatus(@Param("status") Integer status, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(
            value = "SELECT * FROM data as d WHERE d.date > :startDate AND d.date < :endDate",
            nativeQuery = true)
    List<Data> findAllByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<Data> findAllByAccount(Account account);

    @Query(value = "select date_only, COUNT(utm_medium) from data GROUP BY date_only", nativeQuery = true)
    List<Object> statisticUtmMedium();

    @Query(value = "select date_only, COUNT(utm_medium) from data WHERE utm_medium = :medium  GROUP BY date_only", nativeQuery = true)
    List<Object> statisticUtmMediumByMedium(String medium);

    //    value = "select id,date_only, sum(price) as price from data GROUP BY date_only ORDER BY date_only DESC LIMIT 50"
    @Query(nativeQuery = true)
    List<StatisticalRevenueByDayDto> statisticcalrevenueByDay();

    @Query(nativeQuery = true)
    List<KetQuaThongKeUtmDto> thongKeUtmTheoThoiGian_admin(Long startDate, Long endDate);

    @Query(nativeQuery = true)
    List<KetQuaThongKeUtmDto> thongKeUtmTheoThoiGian(Long startDate, Long endDate, List<String> list);

    @Query("SELECT new com.example.ladiworkservice.dto.KetQuaThongKeTopUtmDto(a.userName, a.fullName, count(d.id)) FROM Account a " +
            "LEFT JOIN UtmMedium u ON a.id = u.account.id " +
            "LEFT JOIN Data d ON u.code = d.utm_medium " +
            "WHERE a.shop like CONCAT('%', :shopCode, '%') " +
            "AND a.role = 'marketing' " +
            "AND d.date <= :endDate AND d.date >= :startDate " +
            "GROUP BY a.userName, a.fullName")
    List<KetQuaThongKeTopUtmDto> thongKeDataCuaTaiKhoanTheoThoiGian(Long startDate, Long endDate, String shopCode);

    @Query(nativeQuery = true)
    List<StatisticalRevenueByDateDto> statisticalRevenueByDate(Long startDate, Long endDate, String codeShop);

    @Query(nativeQuery = true)
    List<StatisticUtmByDateDto> statisticUtmByDate(Long startDate, Long endDate, String shopCode);

    @Query(nativeQuery = true)
    List<StatisticDataByDateAndStatusDto> statisticDataByDateAndStatus(Long startDate, Long endDate, String shopCode);


    @Query(nativeQuery = true)
    List<StatisticDataByDateAndStatusDto> statisticDataByDateAndStatusAndUser(Long startDate, Long endDate, String shopCode, Long userId);

    @Query(nativeQuery = true)
    List<StatisticDataByDateAndStatusDto> statisticQuantityDataByDateAndStatus(Long startDate, Long endDate, String shopCode);

    @Query(nativeQuery = true)
    List<StatisticCostByStatusData> statisticCostByStatusData(Long startDate, Long endDate, String shopCode);

    @Query(value = "SELECT DISTINCT(shopcode) shopCode from data where date >= :startDate and date <= :endDate and nhanvienid = :id", nativeQuery = true)
    List<String> findUniqueShop(Long startDate, Long endDate, Long id);


    List<Data> findAllByPhoneAndShopCode(String phone, String shopCode);

    List<Data> findAllByCostIsNotNull();

    @Transactional
    @Modifying
    @Query(value = "update data SET status = :status where id in :ids", nativeQuery = true)
    void findAllByIds(List<Long> ids, int status);

    @Query("SELECT d FROM Data d " +
            "WHERE d.date >= :startDate " +
            " AND d.date <= :endDate " +
            " AND d.shopCode = :shopCode ")
    List<Data> getByTimeAndShop(Long startDate, Long endDate, String shopCode);

    @Query(value = "SELECT d.* FROM data d,work w WHERE \n" +
            "d.shopCode = :shopCode \n" +
            "AND d.nhanvienid = :nhanvienid \n" +
            "AND w.shopcode = :shopCode \n" +
            "AND w.nhanvienid =:nhanvienid \n" +
            "and w.active = 0 \n" +
            "AND d.datechanged  >=w.timeint;", nativeQuery = true)
    List<Data> getDataByDateAndShopByNhanvienID( String shopCode, Long nhanvienid);

    @Query(value = "SELECT d.* FROM data d,work w WHERE \n" +
            "d.shopCode = :shopCode \n" +
            "AND (d.nhanvienid = :nhanvienid OR d.saleid=:nhanvienid )\n" +
            "AND w.shopcode = :shopCode \n" +
            "AND w.nhanvienid =:nhanvienid \n" +
            "and w.active = 0 \n" +
            "AND d.datechanged  >=w.timeint ", nativeQuery = true)
    List<Data> getDataByDateAndShopByNhanvienIDAndsaleID( String shopCode, Long nhanvienid);

    @Query(value = "SELECT  d.* FROM data d,work w\n" +
            "WHERE SUBSTR(d.time_order_success, 1, 8)=:date \n" +
            "AND d.shopCode = :shopCode \n" +
            "AND (d.nhanvienid = :nhanvienid OR d.saleid=:nhanvienid )\n" +
            "AND w.shopcode = :shopCode \n" +
            "AND w.nhanvienid =:nhanvienid \n" +
            "and w.active = 0 \n" +
            "AND d.datechanged  >=w.timeint ", nativeQuery = true)
    List<Data> getDataByDateAndShopByOrderSuccess(Long date, String shopCode, Long nhanvienid);

    @Query(value = "SELECT SUM(price) from data where date >= :startDate and date <= :endDate and shopcode = :shopCode and status in (7,8,10,11)", nativeQuery = true)
    Long statisticRevenue(Long startDate, Long endDate, String shopCode);

    public Data findAllByShippingCode(String code);

    @Query(value = "SELECT * FROM Data d WHERE d.id = :id", nativeQuery = true)
    Data findDataById(@Param("id") Long id);

    @Query("SELECT new com.example.ladiworkservice.controller.reponse.StatisticGeneralResponse(SUM(d.totalProductValue), SUM(d.cost), SUM(d.price), COUNT(d.id))" +
            " FROM Data d " +
            "where d.dateOnly <= :endDate and d.dateOnly >= :startDate and d.shopCode = :shopCode and d.status in (7,8,10,12,13,14,15,16,17,18,19)" )
    StatisticGeneralResponse statisticsGeneral(Long startDate, Long endDate, String shopCode);

    @Query("SELECT new com.example.ladiworkservice.controller.reponse.StatisticsRevenueResponse(SUM(d.totalProductValue), SUM(d.cost), SUM(d.price), COUNT(d.id))" +
            " FROM Data d " +
            "where d.timeFinanced <= :endDate and d.timeFinanced >= :startDate and d.shopCode = :shopCode and d.status in (15)" )
    StatisticsRevenueResponse statisticsRevenue(Long startDate, Long endDate, String shopCode);




}
