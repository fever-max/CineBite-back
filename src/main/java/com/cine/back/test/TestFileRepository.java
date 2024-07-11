package com.cine.back.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestFileRepository extends JpaRepository<TestFileEntity, Integer> {

}
