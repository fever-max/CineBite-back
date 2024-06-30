package com.cine.back.search.dto;

import java.util.List;

public record SearchRequest(String userId, List<String> keywords) {}
