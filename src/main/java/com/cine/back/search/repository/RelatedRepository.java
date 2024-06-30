package com.cine.back.search.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.back.search.entity.RelatedEntity;

@Repository
public interface RelatedRepository extends JpaRepository<RelatedEntity, Integer> {

    // 주어진 SearchEntity와 연관된 Secondary Keyword로 조회
    RelatedEntity findBySearchRelatedWordAndSearchEntity_SearchListNo(
            String searchRelatedWord, int searchListNo);

    // 연관검색어 조회
    List<RelatedEntity> findAllBySearchRelatedWord(String secondaryKeyword);

}
