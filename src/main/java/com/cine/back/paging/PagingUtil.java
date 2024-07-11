package com.cine.back.paging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.cine.back.favorite.dto.FavoriteResponseDto;
import com.cine.back.favorite.entity.UserFavorite;
import com.cine.back.favorite.repository.UserFavoriteRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PagingUtil {

    private final UserFavoriteRepository userFavoriteRepository;

    public static PageRequest createPageRequest(int page, int size, String sortBy, Sort.Direction direction) {
        return PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
    }

    public Page<FavoriteResponseDto> getFavoriteListPaged(String userId, Pageable pageable) {
        PageRequest pageRequest = PagingUtil.createPageRequest(
            pageable.getPageNumber(), // 요청할 페이지 번호
            pageable.getPageSize(), // 한 페이지에 나타낼 항목 수
            "favoriteId",   // 해당 필드를 기준으로 정렬
            Sort.Direction.DESC);
        Page<UserFavorite> userFavorites = userFavoriteRepository.findByUserId(userId, pageRequest);

        return userFavorites.map(userFavorite -> new FavoriteResponseDto(
                userFavorite.getFavoriteId(),
                userFavorite.getUserId(),
                userFavorite.getMovieId(),
                userFavorite.getPosterPath(),
                userFavorite.getTitle(),
                userFavorite.getTomatoScore()
        ));
    }

}