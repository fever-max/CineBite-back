package com.cine.back.user.dto.request;

import lombok.Data;

@Data
public class PasswordValidationRequest {
    private String userId;
    private String userPwd;
}
