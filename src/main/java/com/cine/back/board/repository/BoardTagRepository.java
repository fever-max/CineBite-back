package com.cine.back.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.board.entity.BoardTagEntity;

import java.util.Optional;

public interface BoardTagRepository extends JpaRepository<BoardTagEntity, Long> {

    Optional<BoardTagEntity> findByName(String name);

}
