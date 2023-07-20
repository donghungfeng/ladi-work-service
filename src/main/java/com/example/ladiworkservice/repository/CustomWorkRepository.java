package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Account;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

public interface CustomWorkRepository<Work, Long extends Serializable> extends Repository {
    public List<Work> finWorkByConditions(String startDate, String endDate, Account account, String shopCode, int isActive );
}
