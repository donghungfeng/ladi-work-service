package com.example.ladiworkservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long>,JpaSpecificationExecutor<T>, PagingAndSortingRepository<T, Long> {
    List<T> findAll();
    T findAllById(Long id);
    List<T> findAllByOrderByIdDesc();
}
