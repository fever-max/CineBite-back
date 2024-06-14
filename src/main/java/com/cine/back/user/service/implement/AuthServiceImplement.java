package com.cine.back.user.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cine.back.user.dto.request.IdCheckRequestDto;
import com.cine.back.user.dto.response.IdCheckResponseDto;
import com.cine.back.user.dto.response.ResponseDto;
import com.cine.back.user.repository.UserRepository;
import com.cine.back.user.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;

    // ID 중복 확인
    @Override
    public ResponseEntity<? super IdCheckResponseDto> userIdCheck(IdCheckRequestDto dto) {
        try {
            String userId = dto.getUserId();
            boolean isExistId = userRepository.existsByUserId(userId);
            if (isExistId) {
                return IdCheckResponseDto.duplicateId();     
            }    
        } catch (Exception e) {         
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return IdCheckResponseDto.success();
    }
}
