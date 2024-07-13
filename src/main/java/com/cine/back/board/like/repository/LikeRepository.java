package com.cine.back.board.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cine.back.board.like.entity.LikeEntity;
import com.cine.back.board.post.entity.PostEntity;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    Optional<LikeEntity> findByPostAndUserId(PostEntity postEntity, String userId);

}
