package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends BaseRepository<Account> {
    Account findByUserName(String userName);

    @Query(value = "select c from Account c where c.shop like CONCAT('%', :shopcode, '%') and c.role = 'marketing' ")
    List<Account> getMktByShop(String shopcode);
}
