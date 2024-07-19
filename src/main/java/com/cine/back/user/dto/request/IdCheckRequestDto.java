package com.cine.back.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IdCheckRequestDto {
    
    @NotBlank
    private String userId;
}
