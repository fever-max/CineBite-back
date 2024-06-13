package com.cine.back.movieList.service;

import com.cine.back.movieList.entity.TrendMovieEntity;
import com.cine.back.movieList.repository.TrendMovieRepository;
import com.cine.back.movieList.response.TrendMovieResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MovieListFetcher {

    @Value("${movieList.access-token}")
    private String accessToken;

    @Value("${movieList.urlHead}")
    private String urlHead;

    @Value("${movieList.urlTail}")
    private String urlTail;

    @Value("${movieList.urlweek}")
    private String week; 

    
    private final TrendMovieRepository trendMovieRepository;
    
    // 서버 실행 시 자동 저장
    @PostConstruct
    public void init() {
        try {
            getAllTrendMovies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TrendMovieEntity> getAllTrendMovies() {
    List<TrendMovieEntity> allMovies = new ArrayList<>();

    for (int page = 1; page <= 1; page++) {
        TrendMovieResponse trendMovieResponse = getTrendMovieList(page);
            if (trendMovieResponse != null) {
                List<TrendMovieEntity> movies = trendMovieResponse.getResults();
                allMovies.addAll(movies);
            }
        }
        return allMovies;
    }

    public TrendMovieResponse getTrendMovieList(int page) {
        OkHttpClient client = new OkHttpClient();
        String url = urlHead + week + urlTail;

        Request request = new Request.Builder()
            .url(url + page)
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer " +accessToken)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            TrendMovieResponse trendMovieResponse = parseTrendMovieResponse(responseBody);
            saveTrendMovies(trendMovieResponse.getResults());
            log.info("영화 목록 반환 컨트롤러 확인, trendMovieResponse : {}", trendMovieResponse);
            
            return trendMovieResponse;
        } catch (IOException e) {
            e.printStackTrace();
            log.error("리스트 반환 실패2 : ", e);
            return null;
        }
    }

    // 데이터 저장 
    private void saveTrendMovies(List<TrendMovieEntity> trendMovies) {
        for (TrendMovieEntity movie : trendMovies) {
            Optional<TrendMovieEntity> optionalExistingMovie = trendMovieRepository.findByMovieId(movie.getMovieId());
            if(optionalExistingMovie.isPresent()){
                TrendMovieEntity existingMovie = optionalExistingMovie.get();
                existingMovie.setTitle(movie.getTitle());
                existingMovie.setOverview(movie.getOverview());
                trendMovieRepository.save(existingMovie);
            }else{
                trendMovieRepository.save(movie);
            }
        }
    }
    

    // JSON 문자열을 TrendMovieResponse 객체로 변환
    private TrendMovieResponse parseTrendMovieResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseBody, TrendMovieResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
