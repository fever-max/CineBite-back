package com.cine.back.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.post.entity.PostTagEntity;

public interface TagRepository extends JpaRepository<PostTagEntity, Long> {

}
