package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.ListLogWork;
import org.springframework.data.jpa.repository.Query;
import java.util.*;
import java.util.Optional;

public interface ListLogWorkRepository extends BaseRepository<ListLogWork> {
    @Query(value = "SELECT * FROM list_log_work llw WHERE llw.user_name = :username AND llw.time = :time AND llw.check_in_time IS NOT NULL AND llw.check_out_time IS NULL LIMIT 1", nativeQuery = true)
    ListLogWork findUserWithCheckedIn(String username, Long time);
    @Query(value = "SELECT * FROM list_log_work llw WHERE llw.user_name = :username AND llw.time = :time AND llw.check_in_time IS NOT NULL AND llw.check_out_time IS NOT NULL LIMIT 1", nativeQuery = true)
    ListLogWork findUserWithCheckedOut(String username, Long time);
    @Query(value = "SELECT * FROM list_log_work llw WHERE llw.time >= :startDate AND llw.time <= :endDate", nativeQuery = true)
    List<ListLogWork> findDataByDate(Long startDate, Long endDate);
}
