package com.cine.back.user.dto.response;

import java.util.Map;

public class NaverResponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    public NaverResponse(Map<String, Object> attribute) {

        this.attribute = (Map<String, Object>) attribute.get("response");
    }

    @Override
    public String getProvider() {

        return "naver";
    }

    @Override
    public String getProviderId() {

        return attribute.get("id").toString();
    }

    @Override
    public String getUserEmail() {

        return attribute.get("email").toString();
    }

    @Override
    public String getUserName() {

        return attribute.get("name").toString();
    }
}