package com.cine.back.search.service;

import com.cine.back.search.entity.RelatedEntity;
import com.cine.back.search.entity.SearchEntity;
import com.cine.back.search.repository.RelatedRepository;
import com.cine.back.search.repository.SearchRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelatedService {

    private final RelatedRepository relatedRepository;
    private final SearchRepository searchRepository;

    // 연관 검색어 저장
    // primaryKeyword : 기본 검색어
    // secondaryKeyword : 연관 검색어
    @Transactional
    public void saveRelatedEntity(SearchEntity searchEntity, String secondaryKeyword) {
        log.info("연관 검색어 저장 서비스 - 시작");

        // primaryKeyword로 검색어 entity를 조회
        SearchEntity primarySearchEntity = searchRepository.findBySearchKeyword(searchEntity.getSearchKeyword());

        saveOrUpdateRelatedEntity(primarySearchEntity, secondaryKeyword);
        log.info("연관 검색어 저장 서비스 - 완료");
    }

    // 연관 검색어 엔티티를 저장 또는 업데이트
    // 새로운 연관 검색어 생성 or 기존에 있던 연관 검색어 카운트 업데이트
    private void saveOrUpdateRelatedEntity(SearchEntity searchEntity, String secondaryKeyword) {
        List<RelatedEntity> relatedEntities = relatedRepository.findAllBySearchRelatedWord(secondaryKeyword);
        RelatedEntity relatedEntity = createRelatedEntity(searchEntity,
                secondaryKeyword, relatedEntities);
        relatedRepository.save(relatedEntity);
    }

    private RelatedEntity createRelatedEntity(SearchEntity searchEntity, String secondaryKeyword,
            List<RelatedEntity> relatedEntities) {
        if (relatedEntities.isEmpty()) {
            return newRelatedEntity(searchEntity, secondaryKeyword);
        }
        // get(0) : 첫번째 요소를 가져오는 이유?
        // 이미 존재하는 연관검색어는 하나만 존재함
        return updateRelatedEntity(relatedEntities.get(0));
    }

    private RelatedEntity newRelatedEntity(SearchEntity searchEntity, String secondaryKeyword) {
        RelatedEntity relatedEntity = new RelatedEntity();
        relatedEntity.setSearchEntity(searchEntity);
        relatedEntity.setSearchRelatedWord(secondaryKeyword);
        relatedEntity.setSearchRelatedCount(1);
        log.info("연관 검색어 새로 생성 - 키워드 '{}'", secondaryKeyword);
        return relatedEntity;
    }

    private RelatedEntity updateRelatedEntity(RelatedEntity relatedEntity) {
        relatedEntity.setSearchRelatedCount(relatedEntity.getSearchRelatedCount() +
                1);
        log.info("연관 검색어 카운트 증가 - 키워드 '{}'", relatedEntity.getSearchRelatedWord());
        return relatedEntity;
    }
}
