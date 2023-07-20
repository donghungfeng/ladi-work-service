package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Account;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

public interface CustomDataRepository<Data, Integer extends Serializable> extends Repository {
    public List<Data> finDataByConditions(String status, String startDate, String endDate, Account account, String shopCode, int page, int size);
    public List<Data> checkOut(String status, String startDate, String endDate, Account account, String shopCode);
    List<Data> findDataByAccountNull(String status, String shopCode);

}
