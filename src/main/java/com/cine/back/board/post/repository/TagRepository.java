package com.cine.back.board.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cine.back.board.post.entity.PostTagEntity;

import java.util.*;

public interface TagRepository extends JpaRepository<PostTagEntity, Long> {

    @Query("SELECT t FROM PostTagEntity t JOIN PostTagMapEntity tm ON t.tagNo = tm.tag.tagNo " +
            "GROUP BY t.tagNo, t.tagName " +
            "ORDER BY COUNT(tm.mapNo) DESC")
    List<PostTagEntity> findTopTags(@Param("limit") int limit);

}
