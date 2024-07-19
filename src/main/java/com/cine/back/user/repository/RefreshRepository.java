package com.cine.back.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cine.back.user.entity.RefreshEntity;

import jakarta.transaction.Transactional;
import java.util.*;

public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {

    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);

    List<RefreshEntity> findByUserId(String userId);
    
    List<RefreshEntity> findByExpirationBefore(Date expiration);
}