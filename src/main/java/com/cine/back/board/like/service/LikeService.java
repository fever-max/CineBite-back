package com.cine.back.board.like.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cine.back.board.like.entity.LikeEntity;
import com.cine.back.board.like.repository.LikeRepository;
import com.cine.back.board.post.entity.PostEntity;
import com.cine.back.board.util.EntityUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final EntityUtil entityUtil;

    @Transactional
    public String toggleLike(Long postNo, String userId) {
        PostEntity post = entityUtil.findPostById(postNo);
        Optional<LikeEntity> like = likeRepository.findByPostAndUserId(post, userId);
        if (like.isEmpty()) {
            addLike(post, userId);
            entityUtil.updateLikeCount(post);
            log.info("좋아요 저장 성공");
            return "좋아요 저장";
        } else {
            removeLike(like.get());
            entityUtil.deleteLikeCount(post);
            log.info("좋아요 취소 성공");
            return "좋아요 취소";
        }
    }

    private void addLike(PostEntity post, String userId) {
        LikeEntity likeEntity = LikeEntity.builder()
                .userId(userId)
                .postEntity(post)
                .createdDate(LocalDateTime.now())
                .build();
        likeRepository.save(likeEntity);
    }

    private void removeLike(LikeEntity likeEntity) {
        likeRepository.delete(likeEntity);
    }

}
