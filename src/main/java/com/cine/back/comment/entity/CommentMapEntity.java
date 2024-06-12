package com.cine.back.comment.entity;

import com.cine.back.post.entity.PostEntity;
import com.cine.back.post.entity.PostTagEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Data
// @Entity
// @NoArgsConstructor
// @Table(name = "comment_map")
// public class CommentMapEntity {

// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// @Column(name = "comment_map_no")
// private Long mapNo;

// @ManyToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "post_no")
// private PostEntity post;

// @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
// @JoinColumn(name = "comment_no")
// private CommentEntity comment;

// }
