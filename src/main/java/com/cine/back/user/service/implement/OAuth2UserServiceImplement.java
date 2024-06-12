package com.cine.back.user.service.implement;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cine.back.user.dto.UserDTO;
import com.cine.back.user.dto.response.GoogleResponse;
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
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {

            // oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
            // xx 구글 오어스
        }
        else if (registrationId.equals("kakao")) {
          
          // UserDTO userDTO = new UserDTO();
          // String userId = "kakao_" + oAuth2User.getAttribute("id");
          // UserEntity userEntity = new UserEntity(userId, null, "kakao"); // userEmail이나 userName은 null로 설정
          // userRepository.save(userEntity);
          // userDTO.setUserId(userId);
          // return new CustomOAuth2User(userDTO);
          // xx 카카오 오어스
        }
        else {
            return null;
        }

        int length = Math.min(oAuth2Response.getProviderId().length(), 14);
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId().substring(0, length);
        UserEntity existData = userRepository.findByUserId(username);

        if (existData == null) {

            UserEntity userEntity = new UserEntity();
            userEntity.setUserId(username);
            userEntity.setUserEmail(oAuth2Response.getUserEmail());
            userEntity.setUserName(oAuth2Response.getUserName());
            userEntity.setUserRole("ROLE_USER");
            userEntity.setUserType(registrationId);

            userRepository.save(userEntity);

            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(username);
            userDTO.setUserName(oAuth2Response.getUserName());
            userDTO.setUserRole("ROLE_USER");

            return new CustomOAuth2User(userDTO);
        }
        else {

            existData.setUserEmail(oAuth2Response.getUserEmail());
            existData.setUserName(oAuth2Response.getUserName());

            userRepository.save(existData);

            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(existData.getUserId());
            userDTO.setUserName(oAuth2Response.getUserName());
            userDTO.setUserRole(existData.getUserRole());

            return new CustomOAuth2User(userDTO);
        }
    }
}