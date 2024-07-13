package com.cine.back.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
import com.cine.back.user.entity.UserEntity;

public interface ImgRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserId(String userId);
}
