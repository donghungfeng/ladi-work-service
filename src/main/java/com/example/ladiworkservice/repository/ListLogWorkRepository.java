package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.ListLogWork;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ListLogWorkRepository extends BaseRepository<ListLogWork> {
    @Query(value = "SELECT * FROM list_log_work llw WHERE llw.user_name = :username AND llw.check_in_time IS NOT NULL AND llw.check_out_time IS NULL LIMIT 1", nativeQuery = true)
    ListLogWork findUserWithCheckedIn(String username);
    @Query(value = "SELECT * FROM list_log_work llw WHERE llw.user_name = :username AND llw.check_in_time IS NOT NULL AND llw.check_out_time IS NOT NULL LIMIT 1", nativeQuery = true)
    ListLogWork findUserWithCheckedOut(String username);
}
