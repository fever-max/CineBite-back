package com.cine.back.user.service.implement;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cine.back.user.common.CertificationNumber;
import com.cine.back.user.dto.request.EmailCertificationRequestDto;
import com.cine.back.user.dto.request.IdCheckRequestDto;
import com.cine.back.user.dto.response.EmailCertificationResponseDto;
import com.cine.back.user.dto.response.IdCheckResponseDto;
import com.cine.back.user.dto.UserDTO;
import com.cine.back.user.dto.request.CheckCertificationRequestDto;
import com.cine.back.user.dto.response.CheckCertificationResponseDto;
import com.cine.back.user.dto.response.ResponseDto;
import com.cine.back.user.entity.CertificationEntity;
import com.cine.back.user.entity.UserEntity;
import com.cine.back.user.provider.EmailProvider;
import com.cine.back.user.repository.CertificationRepository;
import com.cine.back.user.repository.UserRepository;
import com.cine.back.user.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;
    private final EmailProvider emailProvider;
    private final PasswordEncoder bCryptPasswordEncoder;

    // 아이디 중복 확인
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

    // 이메일 중복 확인
    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> checkEmail(EmailCertificationRequestDto dto) {
        try {
            String userEmail = dto.getUserEmail();
            boolean isExistEmail = userRepository.existsByUserEmail(userEmail);
            if (isExistEmail) {
                return EmailCertificationResponseDto.duplicateEmail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return EmailCertificationResponseDto.success();
    }

    // 이메일 인증
    @Override
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {
        try {
            String userId = dto.getUserId();
            String userEmail = dto.getUserEmail();

            boolean isExistId = userRepository.existsByUserId(userId);
            if (isExistId) return EmailCertificationResponseDto.duplicateId();
            String certificationNumber = CertificationNumber.getCertificationNumber();

            boolean isSuccessed = emailProvider.sendCertificationMail(userEmail, certificationNumber);
            if (!isSuccessed) return EmailCertificationResponseDto.mailSendFail();
            certificationRepository.deleteByUserEmail(userEmail);
            CertificationEntity certificationEntity = new CertificationEntity(null, userId, userEmail, certificationNumber);
            certificationRepository.save(certificationEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return EmailCertificationResponseDto.success();
    }

    // 이메일 인증 확인
    @Override
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {
        try {
            String userId = dto.getUserId();
            String userEmail = dto.getUserEmail();
            String certificationNumber = dto.getCertificationNumber();

            CertificationEntity certificationEntity = certificationRepository.findByUserId(userId);
            if (certificationEntity == null) return CheckCertificationResponseDto.certificationFail();

            boolean isMatched = certificationEntity.getUserEmail().equals(userEmail) && certificationEntity.getCertificationNumber().equals(certificationNumber);
            if (!isMatched) return CheckCertificationResponseDto.certificationFail();

            certificationRepository.deleteByUserEmail(userEmail);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return CheckCertificationResponseDto.success();
    }

    // 회원가입
    @Override
    public ResponseEntity<? super ResponseDto> join(UserDTO dto) {
        try {
            String userId = dto.getUserName();
            boolean isExistId = userRepository.existsByUserId(userId);
            if(isExistId) return IdCheckResponseDto.duplicateId();

            UserEntity user = UserEntity.builder()
                    .userId(userId)
                    .userPwd(bCryptPasswordEncoder.encode(dto.getUserPwd()))
                    .userEmail(dto.getUserEmail())
                    .userNick(dto.getUserNick())
                    .userRole("ROLE_USER")
                    .userType("web")
                    .apDate(LocalDate.now())
                    .build();
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseEntity.ok(new ResponseDto());
    }

    // 아이디 찾기
    @Override
    public ResponseEntity<? super ResponseDto> findUserId(String userEmail) {
        try {
            UserEntity user = userRepository.findByUserEmail(userEmail);
            if (user != null) {
                String userIdPart = user.getUserId().substring(0, 3) + "***";
                emailProvider.sendCertificationMail(userEmail, "회원님의 아이디는 " + userIdPart + "입니다.");
                return ResponseEntity.ok(new ResponseDto());
            } else {
                return ResponseEntity.badRequest().body(new ResponseDto("해당 이메일로 등록된 아이디가 없습니다.", userEmail));
            }
        } catch (Exception e) {
            return ResponseDto.databaseError();
        }
    }

    // 비밀번호 찾기
    @Override
    public ResponseEntity<? super ResponseDto> resetUserPwd(String userEmail, String userId) {
        try {
            UserEntity user = userRepository.findByUserEmailAndUserId(userEmail, userId);
            if (user != null) {
                String randomUserPwd = generateRandom();
                String hashedUserPwd = bCryptPasswordEncoder.encode(randomUserPwd);
                user.setUserPwd(hashedUserPwd);
                userRepository.save(user);

                emailProvider.sendCertificationMail(userEmail, "회원님의 임시 비밀번호는 " + randomUserPwd + "입니다.");
                return ResponseEntity.ok(new ResponseDto());
            } else {
                return ResponseEntity.badRequest().body(new ResponseDto("해당 이메일과 아이디로 등록된 사용자가 없습니다.", userEmail));
            }
        } catch (Exception e) {
            return ResponseDto.databaseError();
        }
    }

    private String generateRandom() {
        int length = 8;
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String number = "0123456789";
        String userPwd = lowerCase + number;
        Random random = new Random();
        StringBuilder password = new StringBuilder(length);

        password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        password.append(number.charAt(random.nextInt(number.length())));

        for (int i = 2; i < length; i++) {
            password.append(userPwd.charAt(random.nextInt(userPwd.length())));
        }
        return password.toString();
    }
}
