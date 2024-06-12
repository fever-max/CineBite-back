package com.cine.back.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.comment.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

}
