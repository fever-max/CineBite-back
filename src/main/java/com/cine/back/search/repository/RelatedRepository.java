package com.cine.back.search.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.back.search.entity.RelatedEntity;

@Repository
public interface RelatedRepository extends JpaRepository<RelatedEntity, Integer> {
    // 연관검색어 조회
    List<RelatedEntity> findBySearchListNoIn(List<Integer> searchListNos);

    // List<RelatedEntity> findAllBySearchRelatedWord(String secondaryKeyword);
}
