package com.cine.back.user.dto.oauth2;

public interface OAuth2Response {

    String getProvider(); // naver, kakao, google
    String getProviderId(); // id
    String getUserEmail();
    String getUserNick();
}
