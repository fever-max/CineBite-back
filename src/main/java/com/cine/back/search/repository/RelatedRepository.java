package com.cine.back.search.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.back.search.entity.RelatedEntity;

@Repository
public interface RelatedRepository extends JpaRepository<RelatedEntity, Integer> {
    List<RelatedEntity> findBySearchKeywordOrderBySearchRelatedCountDesc(String searchKeyword);

    RelatedEntity findBySearchListNoAndSearchRelatedWord(int searchListNo, String searchRelatedWord);
}
