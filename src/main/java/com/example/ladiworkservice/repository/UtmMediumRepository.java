package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Account;
import com.example.ladiworkservice.model.UtmMedium;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtmMediumRepository extends BaseRepository<UtmMedium> {
    List<UtmMedium> findAll();

    List<UtmMedium> findAllByAccount(Account account);

    @Query("SELECT u FROM UtmMedium u" +
            " WHERE LOWER(u.code) in :utm ")
    List<UtmMedium> getByListUtm(List<String> utm);
}
