package com.cine.back.board.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.board.post.entity.PostTagEntity;

public interface TagRepository extends JpaRepository<PostTagEntity, Long> {

}
