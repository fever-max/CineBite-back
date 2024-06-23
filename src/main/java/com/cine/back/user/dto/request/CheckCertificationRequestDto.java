package com.cine.back.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CheckCertificationRequestDto {
    
    @NotBlank
    private String userId;

    @Email
    @NotBlank
    private String userEmail;

    @NotBlank
    private String certificationNumber;
}
