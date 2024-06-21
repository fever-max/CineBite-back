package com.cine.back.search.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.back.search.entity.SearchEntity;

@Repository
public interface SearchRepository extends JpaRepository<SearchEntity, Integer> {

     // 사용자의 최근 검색 기록
     List<SearchEntity> findByUserEmailOrderBySearchListTimeDesc(String userEmail);
 }
