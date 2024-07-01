package com.cine.back.board.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cine.back.board.post.entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    // 최신 게시글 3개 조회
    List<PostEntity> findTop3ByOrderByCreatedDateDesc();

    // 인기 게시글 3개 조회
    List<PostEntity> findTop3ByOrderByHitCountDesc();

}
