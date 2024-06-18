package com.cine.back.user.service;

import org.springframework.http.ResponseEntity;

import com.cine.back.user.dto.request.EmailCertificationRequestDto;
import com.cine.back.user.dto.request.IdCheckRequestDto;
import com.cine.back.user.dto.response.EmailCertificationResponseDto;
import com.cine.back.user.dto.response.IdCheckResponseDto;
import com.cine.back.user.dto.request.CheckCertificationRequestDto;
import com.cine.back.user.dto.response.CheckCertificationResponseDto;

public interface AuthService {
    
    ResponseEntity<? super IdCheckResponseDto> userIdCheck(IdCheckRequestDto dto);

    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);

    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);
}
