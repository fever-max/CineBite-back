package com.cine.back.board.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.board.comment.entity.ReplyEntity;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    List<ReplyEntity> findByComment_CommentNo(Long commentNo);

}
