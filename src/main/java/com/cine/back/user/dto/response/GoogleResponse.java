package com.cine.back.user.dto.response;

import java.util.Map;

public class GoogleResponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {

        this.attribute = attribute;
    }

    @Override
    public String getProvider() {

        return "google";
    }

    @Override
    public String getProviderId() {

        return attribute.get("sub").toString();
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