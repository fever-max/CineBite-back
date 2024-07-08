package com.cine.back.config;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.cine.back.movieList.response.BoxOfficeMovieResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BoxOfficeConfig {

    @Value("${boxOffice.boxOffice-url}")
    private String boxOfficeUrl;

    @Value("${boxOffice.boxOffice-api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getWeeklyDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastWeekDate = currentDate.minusWeeks(1);
        LocalDate sunday = lastWeekDate.with(DayOfWeek.SUNDAY);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return sunday.format(formatter);
    }

    public BoxOfficeMovieResponse getBoxOfficeData() {
        log.info("api - 영화진흥위원회 주간/주말별 인기 상영작 조회 시작");
        String targetDt = getWeeklyDate();
        System.out.println("targetDt" + targetDt);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(boxOfficeUrl) // base URL
                .queryParam("key", apiKey) // 쿼리 파라미터 추가
                .queryParam("itemPerPage", 10) // 쿼리 파라미터 추가
                .queryParam("targetDt", targetDt) // 쿼리 파라미터 추가
                .build();

        String url = uriComponents.toUriString(); // URI를 문자열로 변환합니다.
        log.info("api - 영화진흥위원회 주간/주말별 인기 상영작 조회 url: {}", url);
        return restTemplate.getForObject(url, BoxOfficeMovieResponse.class);
    }
}
