package com.cine.back.search.dto;

import java.util.List;

public record SearchRequest(int searchListNo, String userId, List<String> keywords) {
}
