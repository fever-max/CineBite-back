package com.cine.back.user.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_list")
@Table(name = "user_list")
public class UserEntity {
    
    //@Entity(name="user_list") UserEntity를 엔터티 클래스로 사용하겠다 jpa이름은 유저리스트
    //@Table(name = "user_list") 데이터베이스의 유저리스트 라는 테이블과 맵핑하겠다

    @Id
    @Column(length = 50, name = "user_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;
    
    @Column(length = 50, name = "user_id")
    private String userId;

    @Column(length = 300, name = "user_pwd")
    private String userPwd;

    @Column(length = 300, name = "user_email")
    private String userEmail;

    @Column(length = 50, name = "user_type")
    private String userType;

    @Column(length = 50, name = "user_role")
    private String userRole;

    // @Column(name = "ap_date")
    // private LocalDate apDate;
}
