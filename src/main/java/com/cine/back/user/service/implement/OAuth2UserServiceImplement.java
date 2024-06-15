package com.cine.back.user.service.implement;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cine.back.user.dto.UserDTO;
import com.cine.back.user.dto.response.GoogleResponse;
import com.cine.back.user.dto.response.KakaoResponse;
import com.cine.back.user.dto.response.NaverResponse;
import com.cine.back.user.dto.response.OAuth2Response;
import com.cine.back.user.entity.CustomOAuth2User;
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

        log.info("OAuth2User attributes: " + oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = getOAuth2Response(registrationId, oAuth2User.getAttributes());

        if (oAuth2Response == null) {
            return null;
        }

        String username = getUsername(oAuth2Response);
        UserEntity userEntity = userRepository.findByUserId(username);

        if (userEntity == null) {
            userEntity = createUserEntity(username, oAuth2Response, registrationId);
        } else {
            updateUserEntity(userEntity, oAuth2Response);
        }

        userRepository.save(userEntity);

        UserDTO userDTO = new UserDTO(userEntity.getUserId(), userEntity.getUserName(), userEntity.getUserRole());
        return new CustomOAuth2User(userDTO);
    }

    private OAuth2Response getOAuth2Response(String registrationId, Map<String, Object> attributes) {
        switch (registrationId) {
            case "naver":
                return new NaverResponse(attributes);
            case "google":
                return new GoogleResponse(attributes);
                // oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
                // xx 구글 오어스
            case "kakao":
                // UserDTO userDTO = new UserDTO();
                // String userId = "kakao_" + oAuth2User.getAttribute("id");
                // UserEntity userEntity = new UserEntity(userId, null, "kakao"); // userEmail이나 userName은 null로 설정
                // userRepository.save(userEntity);
                // userDTO.setUserId(userId);
                // return new CustomOAuth2User(userDTO);
                // xx 카카오 오어스
                return new KakaoResponse(attributes);
            default:
                throw new UnsupportedOperationException("Unsupported OAuth2 provider: " + registrationId);
        }
    }

    private String getUsername(OAuth2Response oAuth2Response) {
        int userIdMaxLength = Math.min(oAuth2Response.getProviderId().length(), 14);
        return oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId().substring(0, userIdMaxLength);
    }

    private UserEntity createUserEntity(String username, OAuth2Response oAuth2Response, String registrationId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(username);
        userEntity.setUserEmail(oAuth2Response.getUserEmail());
        userEntity.setUserName(oAuth2Response.getUserName());
        userEntity.setUserRole("ROLE_USER");
        userEntity.setUserType(registrationId);
        return userEntity;
    }

    private void updateUserEntity(UserEntity userEntity, OAuth2Response oAuth2Response) {
        userEntity.setUserEmail(oAuth2Response.getUserEmail());
        userEntity.setUserName(oAuth2Response.getUserName());
    }
}
