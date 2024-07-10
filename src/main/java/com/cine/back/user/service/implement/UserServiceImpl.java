package com.cine.back.user.service.implement;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cine.back.user.dto.UserDTO;
import com.cine.back.user.entity.UserEntity;
import com.cine.back.user.repository.UserRepository;
import com.cine.back.user.service.UserService;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO get(String userId) { // 로그인한 사용자 정보 가져오기
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        UserDTO userDto = UserDTO.builder()
                .userNick(user.getUserNick())
                .userEmail(user.getUserEmail())
                .userProfileImg(user.getUserProfileImg())
                .userName(user.getUserId())
                .userRole(user.getUserRole())
                .userNo(user.getUserNo())
                .userType(user.getUserType())
                .build();
        return userDto;
    }
}
