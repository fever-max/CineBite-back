package com.cine.back.movieList.service;

import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cine.back.movieList.entity.movieDetailEntity;
import com.cine.back.movieList.repository.MovieDetailRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Slf4j
@Service
public class MovieDetailFetcher {

    @Value("${movieDetail.urlHead}")
    private String urlHead;

    @Value("${movieDetail.urlTail}")
    private String urlTail;

    @Value("${movieList.access-token}")
    private String accessToken;

    private final MovieDetailRepository movieDetailRepository;

    public MovieDetailFetcher(MovieDetailRepository movieDetailRepository) {
        this.movieDetailRepository = movieDetailRepository;
    }

    // synchronized : 여러 요청이 들어와도 안전해서 동시성 문제 방지?
    public synchronized Optional<movieDetailEntity> getMovieDetail(int movieId) {
        Optional<movieDetailEntity> existingMovieDetail = movieDetailRepository.findByMovieId(movieId);
        // 이미 존재하는 영화번호 DB에 있나 순회
        if (existingMovieDetail.isPresent()) {
            log.info("이미 DB에 저장된 영화 상세 정보, movie_id: {}", movieId);
            return existingMovieDetail;
        }

        OkHttpClient client = new OkHttpClient();
        String url = urlHead + movieId + urlTail;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        log.info("영화 상세정보 번호 - \n , movieId : {}", movieId);

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("유효하지 않은 데이터 : " + response);
            }
            String responseBody = response.body().string();
            movieDetailEntity movieDetail = parseMovieDetailResponse(responseBody);

            // 중복된 영화번호가 없고 null이 아니라면 가져온 상세 정보를 저장
            if (movieDetail != null) {
                movieDetailRepository.save(movieDetail);
                log.info("영화 상세 정보 반환 성공 : \n -> movieId : {}", movieDetail);
                return Optional.of(movieDetail);    // 응답 값이 null 이 아니라면 movieDetail 포함하는 optional 객체 포함
            } else {
                log.error("유효하지 않은 영화 상세 정보, movie_id: {}", movieId);
                return Optional.empty();
            }

        } catch (IOException e) {
            e.printStackTrace();
            log.error("상세 목록 반환 실패 : ", e);
            return Optional.empty();    // 응답 값이 null 일 경우 비어있는 optional 객체 생성
        }
    }

    private movieDetailEntity parseMovieDetailResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // json에서 매핑되지 않은 속성이 있어도 무시
            return objectMapper.readValue(responseBody, movieDetailEntity.class);
        } catch (IOException e) {
            log.error("JSON 파싱 실패: ", e);
            return null;
        }
    }
}
