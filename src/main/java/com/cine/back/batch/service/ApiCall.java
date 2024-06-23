package com.cine.back.batch.service;


import com.cine.back.batch.response.BoxOfficeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ApiCall {

    @Value("${movie.api-key}")
    private String apiKey;

    @Value("${movie.url}")
    private String url;

    public String getCurrentDate(){
        LocalDate currentDate = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return currentDate.format(formatter);
    }

    public BoxOfficeResponse getDailyBoxOffice(){
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("key", apiKey)
                .queryParam("targetDt", getCurrentDate())
                .queryParam("itemPerPage", 10)
                .queryParam("multiMovieYn", "Y")
                .queryParam("repNationCd", "K");

        return restTemplate.getForObject(uriBuilder.toUriString(), BoxOfficeResponse.class);
    }
}
