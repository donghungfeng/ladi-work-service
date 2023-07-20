package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.BoL;
import com.example.ladiworkservice.model.BoLDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoLDetailRepository extends BaseRepository<BoLDetail>{
    List<BoLDetail> findAllByBoLId(Long id);

    List<BoLDetail> findAllByBoL(BoL boL);
    @Query(value = "SELECT * from bol_detail bd WHERE bd.sub_product  = :id ORDER BY bd.tciq DESC Limit 1", nativeQuery = true)
    BoLDetail checkTotalImport(Long id);

    @Query(value = "select * from bol_detail where sub_product = :id and tciq >= :sold order by tciq ASC limit 1", nativeQuery = true)
    BoLDetail checkPriceImport(Long id, Long sold);
}
