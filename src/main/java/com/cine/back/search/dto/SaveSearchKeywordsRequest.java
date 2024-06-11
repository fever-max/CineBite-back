package com.cine.back.search.dto;

import java.util.List;

public record SaveSearchKeywordsRequest(String userId, List<SearchKeywordDTO> keywordListDTO) {}
