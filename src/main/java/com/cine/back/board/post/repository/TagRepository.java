package com.cine.back.board.post.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cine.back.board.post.entity.PostTagEntity;

import java.util.*;

@Repository
public interface TagRepository extends JpaRepository<PostTagEntity, Long> {

    List<PostTagEntity> findTop5ByOrderByCreatedDateDesc();

    @Query("SELECT pt FROM PostTagEntity pt GROUP BY pt.tagName ORDER BY COUNT(pt.tagName) DESC")
    List<PostTagEntity> findTopUsedTags(Pageable pageable);

    @Query("SELECT DISTINCT pm.post.postNo " +
            "FROM PostTagMapEntity pm " +
            "WHERE pm.tag.tagName = :tagName")
    List<Long> findPostNumbersByTagName(@Param("tagName") String tagName);

}
