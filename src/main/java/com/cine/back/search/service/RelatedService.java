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
import java.util.ArrayList;
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
        log.info(
                "연관 검색어 저장 서비스 - previousSearchListNo: {}",
                previousSearchListNo);
        List<String> keywords = searchRequest.keywords();
        for (String keyword : keywords) {
            processRelatedEntity(previousSearchListNo, keyword);
        }
        log.info("연관 검색어 저장 서비스 - 완료");
    }

    public List<RelatedEntity> processRelatedEntity(int previousSearchListNo, String keyword) {
        log.info("연관 검색어 저장/업데이트 서비스 - 시작");

        SearchEntity searchEntity = searchRepository.findBySearchListNo(previousSearchListNo);
        if (searchEntity == null) {
            log.warn(
                    "연관 검색어 저장/업데이트 실패 - previousSearchListNo: {}",
                    previousSearchListNo);
            return Collections.emptyList();
        }
        List<RelatedEntity> relatedEntities = saveRelatedEntity(searchEntity, keyword);
        log.info("연관 검색어 저장/업데이트 서비스 - 완료");
        return relatedEntities;
    }

    private List<RelatedEntity> saveRelatedEntity(SearchEntity searchEntity, String keyword) {
        RelatedEntity relatedEntity = createRelatedEntity(searchEntity, keyword);
        relatedRepository.save(relatedEntity);
        log.info("연관 검색어 저장/업데이트 - 키워드 '{}'", keyword);
        List<RelatedEntity> resultList = new ArrayList<>();
        resultList.add(relatedEntity);
        return resultList;
    }

    private RelatedEntity createRelatedEntity(SearchEntity searchEntity, String keyword) {
        RelatedEntity relatedEntity = new RelatedEntity();
        relatedEntity.setSearchListNo(searchEntity.getSearchListNo());
        relatedEntity.setSearchRelatedWord(keyword);
        return relatedEntity;
    }
}
