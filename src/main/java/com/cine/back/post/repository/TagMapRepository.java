package com.cine.back.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.post.entity.PostTagMapEntity;

public interface TagMapRepository extends JpaRepository<PostTagMapEntity, Long> {

}
