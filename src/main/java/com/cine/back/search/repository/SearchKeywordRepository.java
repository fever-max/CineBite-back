package com.cine.back.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.back.search.entity.SearchKeywordEntity;

@Repository
public interface SearchKeywordRepository extends JpaRepository<SearchKeywordEntity, Integer> {

 }
