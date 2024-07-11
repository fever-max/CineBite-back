package com.cine.back.recommendation.service;

import com.cine.back.favorite.entity.UserFavorite;
import com.cine.back.favorite.repository.UserFavoriteRepository;
import com.cine.back.recommendation.dto.RecommendationRequest;
import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.exception.MovieNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class RecommendationService {

    private final UserFavoriteRepository userFavoriteRepository;
    private final MovieDetailRepository movieDetailRepository;

    public List<RecommendationRequest> recommendMovies(String userId) {

        // 현재 사용자의 찜 목록을 가져오기
        List<UserFavorite> currentUserFavorites = userFavoriteRepository.findByUserId(userId).orElse(Collections.emptyList());
        Set<Integer> currentUserMovieIds = currentUserFavorites.stream()
                .map(UserFavorite::getMovieId)
                .collect(Collectors.toSet());

        // 다른 사용자들의 찜 목록을 가져오기
        Map<String, List<UserFavorite>> allUserFavorites = getAllUserFavorites();
        log.info("# [GET][/recommendations] 서비스 - 다른 사용자들의 찜목록 : {} ", allUserFavorites);

        // 현재 사용자와 다른 사용자의 찜 목록을 비교하여 유사한 사용자들을 찾습니다.
        Map<String, Double> similarityScores = new HashMap<>();
        for (Map.Entry<String, List<UserFavorite>> entry : allUserFavorites.entrySet()) {
            String otherUserId = entry.getKey();
            List<UserFavorite> otherUserFavorites = entry.getValue();

            if (!otherUserId.equals(userId)) {
                Set<Integer> otherUserMovieIds = otherUserFavorites.stream()
                        .map(UserFavorite::getMovieId)
                        .collect(Collectors.toSet());

                double similarity = calculateJaccardSimilarity(currentUserMovieIds, otherUserMovieIds);
                similarityScores.put(otherUserId, similarity);
            }
        }

        // 유사한 사용자들의 찜 목록을 기반으로 영화 추천
        Set<Integer> recommendedMovieIds = new HashSet<>();
        List<RecommendationRequest> recommendedMovies = new ArrayList<>();
        similarityScores.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(5) // 상위 5명의 유사한 사용자들로 제한
                .forEach(entry -> {
                    String similarUserId = entry.getKey();
                    List<UserFavorite> similarUserFavorites = allUserFavorites.get(similarUserId);
                    similarUserFavorites.stream()
                            .map(UserFavorite::getMovieId)
                            .filter(movieId -> !currentUserMovieIds.contains(movieId)) // 현재 사용자가 보지 않은 영화만 추천
                            .filter(movieId -> !recommendedMovieIds.contains(movieId)) // 중복 제거
                            .map(this::findMovieById) // 영화 정보를 가져옵니다.
                            .map(this::convertToDto) // 가져오고 싶은 데이터(DTO)로 변환
                            .forEach(movie -> {
                                recommendedMovies.add(movie);
                                recommendedMovieIds.add(movie.getMovieId());
                            });
                });
        log.info("# [GET][/recommendations] 서비스 - 유저 {}의 찜목록 : {} ", userId, recommendedMovies);
        return recommendedMovies;
    }

    // 다른 사용자들의 찜목록 조회
    private Map<String, List<UserFavorite>> getAllUserFavorites() {
        List<UserFavorite> allFavorites = userFavoriteRepository.findAll();
        return allFavorites.stream().collect(Collectors.groupingBy(UserFavorite::getUserId));
    }

    // 유사도 계산
    private double calculateJaccardSimilarity(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        
        Set<Integer> union = new HashSet<>(set1);
        union.addAll(set2);
        
        return (double) intersection.size() / union.size();
    }

    // 영화 정보 조회 및 예외 처리
    private MovieDetailEntity findMovieById(int movieId) {
        return movieDetailRepository.findByMovieId(movieId)
                .orElseThrow(MovieNotFoundException::new);
    }

    // MovieDetailEntity를 MovieDetailDto로 변환
    private RecommendationRequest convertToDto(MovieDetailEntity movie) {
        return new RecommendationRequest(
            movie.getMovieId(),
            movie.getTitle(),
            movie.getPosterPath(),
            movie.getTomatoScore()
        );
    }
}
