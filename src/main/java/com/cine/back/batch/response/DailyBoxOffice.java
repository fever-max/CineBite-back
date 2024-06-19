package com.cine.back.batch.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DailyBoxOffice (
        @JsonProperty("rnum") String rnum,
        @JsonProperty("rank") String rank,
        @JsonProperty("rankInten") String rankInten,
        @JsonProperty("rankOldAndNew") String rankOldAndNew,
        @JsonProperty("movieCd") String movieCd,
        @JsonProperty("movieNm") String movieNm,
        @JsonProperty("openDt") String openDt,
        @JsonProperty("salesAmt") String salesAmt,
        @JsonProperty("salesShare") String salesShare,
        @JsonProperty("salesInten") String salesInten,
        @JsonProperty("salesChange") String salesChange,
        @JsonProperty("salesAcc") String salesAcc,
        @JsonProperty("audiCnt") String audiCnt,
        @JsonProperty("audiInten") String audiInten,
        @JsonProperty("audiChange") String audiChange,
        @JsonProperty("audiAcc") String audiAcc,
        @JsonProperty("scrnCnt") String scrnCnt,
        @JsonProperty("showCnt") String showCnt){
}
