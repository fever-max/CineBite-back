package com.cine.back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userNo;

    @JsonProperty("userId")
    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{5,10}$", message = "문자, 숫자 포함 5~10자리로 입력해주세요.")
    private String userName;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String userEmail;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z!@#$%^&*]{8,13}$", message = "영문, 숫자 포함 8자 이상으로 입력해주세요.")
    private String userPwd;

    private String userType;
    private String userRole;
    private String userProfileImg;
    private String userNick;
    private String apDate;
    @JsonIgnore
    private MultipartFile userProfileFile;
    private String isDelete;
}