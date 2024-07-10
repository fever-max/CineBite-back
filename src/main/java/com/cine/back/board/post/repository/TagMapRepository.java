package com.cine.back.board.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.back.board.post.entity.PostTagMapEntity;

@Repository
public interface TagMapRepository extends JpaRepository<PostTagMapEntity, Long> {

}
