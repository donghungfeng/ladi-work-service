package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.controller.reponse.StatisticBoLByStatusReponse;
import com.example.ladiworkservice.controller.reponse.StatisticBoLRespone;
import com.example.ladiworkservice.controller.reponse.StatisticHistoryBoLReponse;
import com.example.ladiworkservice.model.BoL;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoLRepository extends BaseRepository<BoL>{
    @Query("SELECT new com.example.ladiworkservice.controller.reponse.StatisticBoLByStatusReponse(b.status, COUNT(b.id)) FROM BoL b where b.type = :type and b.shop.code = :shopCode GROUP BY b.status")
    List<StatisticBoLByStatusReponse> statisticBoLByStatus(int type, String shopCode);
    @Query(nativeQuery = true)
    StatisticBoLRespone statisticBoL(int type, Long shopCode);
    @Query("SELECT new com.example.ladiworkservice.controller.reponse.StatisticHistoryBoLReponse(b.type, SUM(bd.totalQuantity)) from BoL b, BoLDetail bd where bd.boL.id = b.id and b.shop.code = :shopCode group by b.type")
    List<StatisticHistoryBoLReponse> statisticHistoryBoL(String shopCode);

}
