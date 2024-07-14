package com.cine.back.recommendation.service;

import com.cine.back.favorite.entity.UserFavorite;
import com.cine.back.favorite.repository.UserFavoriteRepository;
import com.cine.back.recommendation.dto.RecommendationRequest;
import com.cine.back.movieList.entity.MovieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.cine.back.movieList.exception.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class RecommendationService {

    private final UserFavoriteRepository userFavoriteRepository;
    private final MovieDetailRepository movieDetailRepository;

    public Page<RecommendationRequest> recommendMovies(String userId, Pageable pageable) {
        List<UserFavorite> currentUserFavorites = userFavoriteRepository.findByUserId(userId).orElse(Collections.emptyList());
        Set<Integer> currentUserMovieIds = extractMovieIds(currentUserFavorites);
        Map<String, List<UserFavorite>> allUserFavorites = getAllUserFavorites(pageable);
        Map<String, Double> similarityScores = calculateSimilarityScores(userId, currentUserMovieIds, allUserFavorites);
        List<RecommendationRequest> recommendedMovies = generateRecommendations(currentUserMovieIds, similarityScores, allUserFavorites);
        
        log.info("# 추천 영화 목록 : {} ", recommendedMovies);
        return paginateRecommendations(recommendedMovies, pageable);
    }

    
    // 찜 목록에서 영화 ID만 추출
    private Set<Integer> extractMovieIds(List<UserFavorite> favorites) {
        return favorites.stream()
                .map(UserFavorite::getMovieId)
                .collect(Collectors.toSet());
    }

    // 모든 사용자들의 찜 목록 조회
    private Map<String, List<UserFavorite>> getAllUserFavorites(Pageable pageable) {
        List<UserFavorite> allFavorites = userFavoriteRepository.findAll();
        log.info("# 전체 찜 목록 : {} ", allFavorites);
        return allFavorites.stream().collect(Collectors.groupingBy(UserFavorite::getUserId));
    }

    // 현재 사용자와 다른 사용자 간의 유사도 점수를 계산
    private Map<String, Double> calculateSimilarityScores(
        String userId, // 현재 사용자 ID
        Set<Integer> currentUserMovieIds, // 현재 사용자의 영화 ID set
        Map<String, List<UserFavorite>> allUserFavorites) {

        Map<String, Double> similarityScores = new HashMap<>();

        for (Map.Entry<String, List<UserFavorite>> entry : allUserFavorites.entrySet()) {
            String otherUserId = entry.getKey();
            if (!otherUserId.equals(userId)) {
                Set<Integer> otherUserMovieIds = extractMovieIds(entry.getValue());
                double similarity = calculateJaccardSimilarity(currentUserMovieIds, otherUserMovieIds);
                similarityScores.put(otherUserId, similarity);
            }
        }
        log.info("# 유사도 점수 : {} ", similarityScores);
        return similarityScores;
    }

    // 추천 영화목록 생성
    private List<RecommendationRequest> generateRecommendations(
                    Set<Integer> currentUserMovieIds, // 현재 사용자가 찜한 영화 ID 세트
                    Map<String, Double> similarityScores, // 유사 사용자 유사도 점수 맵
                    Map<String, List<UserFavorite>> allUserFavorites)
                    {
        Set<Integer> recommendedMovieIds = new HashSet<>();
        List<RecommendationRequest> recommendedMovies = new ArrayList<>();

        similarityScores.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(5) // 상위 5명의 유사한 사용자들로 제한
                .forEach(entry -> {
                    List<UserFavorite> similarUserFavorites = allUserFavorites.get(entry.getKey());
                    similarUserFavorites.stream()
                            .map(UserFavorite::getMovieId)
                            .filter(movieId -> !currentUserMovieIds.contains(movieId))
                            .filter(movieId -> !recommendedMovieIds.contains(movieId))  // 이미 추천된 영화 제외
                            .map(this::findMovieById) // 영화 정보 가져오기
                            .map(this::convertToDto) // 가져오고 싶은 데이터(DTO)로 변환
                            .forEach(movie -> {
                                recommendedMovies.add(movie);
                                recommendedMovieIds.add(movie.movieId());
                            });
                });
        return recommendedMovies;
    }

    // Jaccard 유사도 계산
    private double calculateJaccardSimilarity(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        
        Set<Integer> union = new HashSet<>(set1);
        union.addAll(set2);
        
        return (double) intersection.size() / union.size();
    }

    private MovieDetailEntity findMovieById(int movieId) {
        return movieDetailRepository.findByMovieId(movieId)
                .orElseThrow(MovieNotFoundException::new);
    }

    // MovieDetailEntity 엔티티를 DTO로 변환
    private RecommendationRequest convertToDto(MovieDetailEntity movie) {
        return new RecommendationRequest(
            movie.getMovieId(),
            movie.getTitle(),
            movie.getPosterPath(),
            movie.getTomatoScore()
        );
    }

    // 추천 영화 목록을 페이지로 변환
    private Page<RecommendationRequest> paginateRecommendations(List<RecommendationRequest> recommendedMovies, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), recommendedMovies.size());
        return new PageImpl<>(recommendedMovies.subList(start, end), pageable, recommendedMovies.size());
    }
}
