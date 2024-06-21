package com.cine.back.user.dto.response;

import java.util.Map;

public class KakaoResponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {

        // this.attribute = (Map<String, Object>) attribute.get("response");
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {

        return "kakao";
    }

    @Override
    public String getProviderId() {

        return attribute.get("id").toString();
    }

    @Override
    public String getUserEmail() {

        // return attribute.get("email").toString();
        Object email = attribute.get("email");
        return email != null ? email.toString() : null;
    }

    @Override
    public String getUserName() {

        // return attribute.get("name").toString();
        Object name = attribute.get("name");
        return name != null ? name.toString() : null;
    }
}