package com.cine.back.user.service.implement;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cine.back.user.dto.UserDTO;
import com.cine.back.user.entity.UserEntity;
import com.cine.back.user.repository.UserRepository;
import com.cine.back.user.service.UserService;
import com.cine.back.user.util.FileUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileUtil fileUtil;

    @Override
    public UserDTO get(String userId) { // 로그인한 사용자 정보 가져오기
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("존재하지 않는 사용자");
        }
        UserDTO userDto = UserDTO.builder()
                .userNick(user.getUserNick())
                .userEmail(user.getUserEmail())
                .userProfileImg(user.getUserProfileImg())
                .userName(user.getUserId())
                .userRole(user.getUserRole())
                .userNo(user.getUserNo())
                .userType(user.getUserType())
                .userPwd(user.getUserPwd())
                .build();
        return userDto;
    }

    @Override
    public boolean validatePassword(String userId, String userPwd) {
        UserEntity user = userRepository.findByUserId(userId);
        if (user == null) {
            return false;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(userPwd, user.getUserPwd());
    }

    @Override
    public UserDTO updateUser(UserDTO request) {
        UserEntity user = userRepository.findByUserId(request.getUserName());
        if (user == null) {
            throw new RuntimeException("존재하지 않는 사용자");
        }
        user.setUserNick(request.getUserNick());
        user.setUserEmail(request.getUserEmail());

        if (request.getUserRole() != null) {
            user.setUserRole(request.getUserRole());
        }
        if (request.getUserType() != null) {
            user.setUserType(request.getUserType());
        }
        if (request.getUserPwd() != null && !request.getUserPwd().isEmpty()) {
            user.setUserPwd(passwordEncoder.encode(request.getUserPwd()));
        }
        userRepository.save(user);
        return UserDTO.builder()
                .userNick(user.getUserNick())
                .userEmail(user.getUserEmail())
                .userName(user.getUserId())
                .userRole(user.getUserRole())
                .userNo(user.getUserNo())
                .userType(user.getUserType())
                .build();
    }

    @Override
    public void modify(UserDTO userDTO) {
        UserEntity user = userRepository.findByUserId(userDTO.getUserName());
        String beforeProfileImage = user.getUserProfileImg();
        String profileImage = null;
        MultipartFile multipartFile = userDTO.getUserProfileFile();

        if (multipartFile != null) {
            profileImage = fileUtil.saveFile(multipartFile, userDTO.getUserName());
            if (beforeProfileImage != null) {
                log.info("기존 프로필 이미지 삭제 시도: " + beforeProfileImage);
                fileUtil.deleteFile(beforeProfileImage);
            }
            user.setUserProfileImg(profileImage);
        }
        if (Boolean.parseBoolean(userDTO.getIsDelete())) {
            if (beforeProfileImage != null) {
                log.info("프로필 이미지 삭제 시도: " + beforeProfileImage);
                fileUtil.deleteFile(beforeProfileImage);
            }
            user.setUserProfileImg(null);
        }
        user.setUserNick(userDTO.getUserNick());
        userRepository.save(user);
    }
}
