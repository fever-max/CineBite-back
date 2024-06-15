package com.cine.back.user.dto.response;

public interface OAuth2Response {

    String getProvider(); 
    String getProviderId(); 
    String getUserEmail();
    String getUserName();
}
