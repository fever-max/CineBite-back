package com.cine.back.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity(name = "user_refresh")
@Table(name = "user_refresh")
public class RefreshEntity {
    
    @Id
    @Column(length = 20, name = "user_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;
    
    @Column(length = 50, name = "user_id")
    private String userId;

    @Column(length = 5000, name = "refresh")
    private String refresh;

    @Column(length = 100, name = "expiration")
    private Date expiration;
}