package com.cine.back.user.dto.oauth2;

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
    public String getUserNick() {
        // return attribute.get("name").toString();
        Object nickname = attribute.get("nickname");
        return nickname != null ? nickname.toString() : null;
    }
}