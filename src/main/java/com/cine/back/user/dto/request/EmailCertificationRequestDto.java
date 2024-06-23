package com.cine.back.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailCertificationRequestDto {
    
    @NotBlank
    private String userId;

    @NotBlank
    @Email
    private String userEmail;
}
