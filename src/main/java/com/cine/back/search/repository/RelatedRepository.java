package com.cine.back.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.search.entity.RelatedEntity;


public interface RelatedRepository extends JpaRepository<RelatedEntity, Integer> {

}
