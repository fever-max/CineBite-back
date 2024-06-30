package com.cine.back.board.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.board.post.entity.PostTagMapEntity;

public interface TagMapRepository extends JpaRepository<PostTagMapEntity, Long> {

}
