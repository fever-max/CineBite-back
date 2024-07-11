package com.cine.back.batch.controller;

import com.cine.back.batch.response.BoxOfficeResponse;
import com.cine.back.batch.service.ApiCall;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/movie")
public class BoxOfficeController {

    private final ApiCall apiCall;

    public BoxOfficeController(ApiCall apiCall) {
        this.apiCall = apiCall;
    }

    @GetMapping("/getMovieInfo")
    public ResponseEntity<BoxOfficeResponse> getBoxOfficeInfo() {
        return ResponseEntity.ok(apiCall.getDailyBoxOffice());
    }
}
