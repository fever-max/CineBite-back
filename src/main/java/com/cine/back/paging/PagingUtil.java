package com.cine.back.paging;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PagingUtil {

    public static PageRequest createPageRequest(int page, int size, String sortBy, Sort.Direction direction) {
        return PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
    }
}