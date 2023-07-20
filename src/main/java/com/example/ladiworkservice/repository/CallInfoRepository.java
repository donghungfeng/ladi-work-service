package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Account;
import com.example.ladiworkservice.model.CallInfo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CallInfoRepository extends BaseRepository<CallInfo> {
    List<CallInfo> findAllByIsActive(int isActive);
    CallInfo findAllByAccount(Account account);
    @Query(value = "SELECT 1 FROM DUAL", nativeQuery = true)
    int heathCheck();
}
