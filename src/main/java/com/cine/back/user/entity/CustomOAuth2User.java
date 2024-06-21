package com.cine.back.user.entity;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cine.back.user.dto.UserDTO;

public class CustomOAuth2User implements OAuth2User {

    private final UserDTO userDTO;

    public CustomOAuth2User(UserDTO userDTO) {

        this.userDTO = userDTO;
    }   

    @Override
    public Map<String, Object> getAttributes() {
        
        return Map.of(
            "userRole", userDTO.userRole(),
            "userName", userDTO.userName(),
            "userId", userDTO.userId()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {// 권한 부여

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return userDTO.userRole();
            }
        });

        return collection;
    }

    @Override
    public String getName() {

        return userDTO.userName();
    }

    public String getUsername() {

        return userDTO.userId();
    }
}