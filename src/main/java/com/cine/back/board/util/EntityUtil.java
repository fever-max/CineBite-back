package com.cine.back.board.util;

import org.springframework.stereotype.Service;

import com.cine.back.board.comment.entity.CommentEntity;
import com.cine.back.board.comment.entity.ReplyEntity;
import com.cine.back.board.comment.repository.CommentRepository;
import com.cine.back.board.comment.repository.ReplyRepository;
import com.cine.back.board.post.entity.PostEntity;
import com.cine.back.board.post.repository.PostRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntityUtil {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    public PostEntity findPostById(Long postNo) {
        log.info("findPostById 실행, 찾는 PostNo: {}", postNo);
        return postRepository.findById(postNo)
                .orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다."));
    }

    public CommentEntity findCommentById(Long commentNo) {
        log.info("findCommentById 실행, 찾는 commentNo: {}", commentNo);
        return commentRepository.findById(commentNo)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));
    }

    public ReplyEntity findReplyById(Long replyNo) {
        log.info("findReplyById 실행, 찾는 replyNo: {}", replyNo);
        return replyRepository.findById(replyNo)
                .orElseThrow(() -> new EntityNotFoundException("대댓글을 찾을 수 없습니다."));
    }

    public void updateCommentCount(Long postNo) {
        log.info("updateCommentCount 실행, postNo: {}", postNo);
        PostEntity post = findPostById(postNo);
        post.setCommentCount(post.getCommentCount() + 1);
    }

    public void deleteCommentCount(Long postNo) {
        log.info("deleteCommentCount 실행, postNo: {}", postNo);
        PostEntity post = findPostById(postNo);
        post.setCommentCount(post.getCommentCount() - 1);
    }

    public PostEntity updateHitCount(PostEntity post) {
        log.info("updateHitCount 실행, postNo: {}", post.getPostNo());
        post.setHitCount(post.getHitCount() + 1);
        return post;
    }

}
