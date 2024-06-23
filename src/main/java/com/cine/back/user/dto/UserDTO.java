package com.cine.back.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userNo;

    @JsonProperty("userId")
    private String userName;

    private String userEmail;
    private String userPwd;
    private String userType;
    private String userRole;
    private String userProfileImg;
    private String userNick;
    private String apDate;
}