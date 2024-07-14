package com.cine.back.search.service;

import com.cine.back.search.dto.SearchRequest;
import com.cine.back.search.entity.RelatedEntity;
import com.cine.back.search.entity.SearchEntity;
import com.cine.back.search.repository.RelatedRepository;
import com.cine.back.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelatedService {

    private final RelatedRepository relatedRepository;
    private final SearchRepository searchRepository;

    @Transactional
    public void saveRelatedEntity(SearchRequest searchRequest) {
        log.info("연관 검색어 저장 서비스 - 시작");
        int previousSearchListNo = searchRequest.searchListNo();
        log.info("연관 검색어 저장 서비스 - previousSearchListNo: {}", previousSearchListNo);
        List<String> keywords = searchRequest.keywords();
        for (String keyword : keywords) {
            processRelatedEntity(previousSearchListNo, keyword);
        }
        log.info("연관 검색어 저장 서비스 - 완료");
    }

    public void processRelatedEntity(int previousSearchListNo, String keyword) {
        log.info("연관 검색어 저장/업데이트 서비스 - 시작");

        SearchEntity searchEntity = searchRepository.findBySearchListNo(previousSearchListNo);
        if (searchEntity == null) {
            log.warn("연관 검색어 저장/업데이트 실패 - previousSearchListNo: {}", previousSearchListNo);
            return;
        }

        RelatedEntity existingRelatedEntity = relatedRepository.findBySearchListNoAndSearchRelatedWord(
                searchEntity.getSearchListNo(), keyword);
        if (existingRelatedEntity != null) {
            updateRelatedEntity(existingRelatedEntity);
        } else {
            saveNewRelatedEntity(searchEntity, keyword);
        }
        log.info("연관 검색어 저장/업데이트 서비스 - 완료");
    }

    private void saveNewRelatedEntity(SearchEntity searchEntity, String keyword) {
        RelatedEntity relatedEntity = createRelatedEntity(searchEntity, keyword);
        relatedRepository.save(relatedEntity);
        log.info("새로운 연관 검색어 저장 - 키워드 '{}'", keyword);
    }

    private RelatedEntity createRelatedEntity(SearchEntity searchEntity, String keyword) {
        RelatedEntity relatedEntity = new RelatedEntity();
        relatedEntity.setSearchListNo(searchEntity.getSearchListNo());
        relatedEntity.setSearchKeyword(searchEntity.getSearchKeyword());
        relatedEntity.setSearchRelatedWord(keyword);
        relatedEntity.setSearchRelatedCount(1);
        return relatedEntity;
    }

    private void updateRelatedEntity(RelatedEntity relatedEntity) {
        relatedEntity.setSearchRelatedCount(relatedEntity.getSearchRelatedCount() + 1);
        relatedRepository.save(relatedEntity);
        log.info("기존 연관 검색어 업데이트 - 키워드 '{}'", relatedEntity.getSearchRelatedWord());
    }

    @Transactional(readOnly = true)
    public List<RelatedEntity> findRelatedByKeyword(String keyword) {
        List<RelatedEntity> relatedEntities = relatedRepository
                .findBySearchKeywordOrderBySearchRelatedCountDesc(keyword);
        if (relatedEntities.isEmpty()) {
            log.info("'{}' 키워드를 포함하는 연관 검색어 조회 실패", keyword);
            return Collections.emptyList();
        }
        log.info("'{}' 키워드를 포함하는 연관 검색어 조회: {}", keyword, relatedEntities);
        return relatedEntities;
    }
}
