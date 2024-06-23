package com.cine.back.user.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_list")
@Table(name = "user_list")
public class UserEntity {

    @Id
    @Column(length = 50, name = "user_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;
    
    @Column(length = 50, name = "user_id")
    private String userId;

    @Column(length = 300, name = "user_email")
    private String userEmail;

    @Column(length = 300, name = "user_pwd")
    private String userPwd;

    @Column(length = 50, name = "user_type")
    private String userType;

    @Column(length = 50, name = "user_role")
    private String userRole;

    @Column(length = 50, name = "user_profileImg")
    private String userProfileImg;

    @Column(length = 50, name = "user_nick")
    private String userNick;

    @Column(name = "ap_date")
    private LocalDate apDate = LocalDate.now();
}
