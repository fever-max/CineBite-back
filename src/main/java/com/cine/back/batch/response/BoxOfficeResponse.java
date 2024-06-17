package com.cine.back.batch.response;
import com.fasterxml.jackson.annotation.JsonProperty;

public record BoxOfficeResponse(@JsonProperty("boxOfficeResult") BoxOfficeResult boxOfficeResult) {
}
