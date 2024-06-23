package com.cine.back.batch.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BoxOfficeResult(
        @JsonProperty("boxofficeType") String boxOfficeType,
        @JsonProperty("showRange") String showRange,
        @JsonProperty("dailyBoxOfficeList") List<DailyBoxOffice> dailyBoxOfficeList) {
}
