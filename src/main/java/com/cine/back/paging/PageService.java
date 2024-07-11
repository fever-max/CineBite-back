package com.cine.back.paging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageService<T> {
    Page<T> getPagedList(String userId, Pageable pageable);
}
