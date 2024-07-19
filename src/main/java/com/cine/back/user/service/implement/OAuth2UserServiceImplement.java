package com.cine.back.user.service.implement;

import java.time.LocalDate;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cine.back.user.dto.UserDTO;
import com.cine.back.user.dto.oauth2.CustomOAuth2User;
import com.cine.back.user.dto.oauth2.GoogleResponse;
import com.cine.back.user.dto.oauth2.KakaoResponse;
import com.cine.back.user.dto.oauth2.NaverResponse;
import com.cine.back.user.dto.oauth2.OAuth2Response;
import com.cine.back.user.entity.UserEntity;
import com.cine.back.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuthResponse = null;

        if (registrationId.equals("naver")) {
            oAuthResponse = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("kakao")) {
            oAuthResponse = new KakaoResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuthResponse = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            log.error("지원하지 않는 소셜 로그인입니다.");
            throw new OAuth2AuthenticationException(new OAuth2Error("unsupported_social_login", "Unsupported Social Login", null));
        }

        String username = oAuthResponse.getProvider() + "" + oAuthResponse.getProviderId();
        if (username.length() > 15) {
            username = username.substring(0, 15);
        }
        UserEntity existData = userRepository.findByUserId(username);

        if (existData == null) {
            String nickname = oAuthResponse.getUserNick() != null ? oAuthResponse.getUserNick() : RandomStringUtils.random(5, true, false);

            UserEntity user = UserEntity.builder()
                    .userId(username)
                    .userEmail(oAuthResponse.getUserEmail())
                    .userType(oAuthResponse.getProvider())
                    .userNick(nickname)
                    .userRole("ROLE_USER")
                    .apDate(LocalDate.now())
                    .build();
            userRepository.save(user);

            UserDTO userDto = UserDTO.builder()
                    .userName(username)
                    .userNick(user.getUserNick())
                    .userRole("ROLE_USER")
                    .build();
            return new CustomOAuth2User(userDto);
        } else {
            existData.setUserEmail(oAuthResponse.getUserEmail());

            UserDTO userDto = UserDTO.builder()
                    .userName(existData.getUserId())
                    .userNick(existData.getUserNick())
                    .userRole(existData.getUserRole())
                    .build();
            return new CustomOAuth2User(userDto);
        }
    }
}
