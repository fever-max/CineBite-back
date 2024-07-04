package com.cine.back.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.cine.back.user.dto.UserDTO;
import com.cine.back.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController implements UserControllerDocs{
    
    private final UserService userService;

    @GetMapping("/data") // 로그인한 사용자 정보 가져오기
    public ResponseEntity<?> get(){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO userDto = userService.get(userId);
        return ResponseEntity.ok(userDto);
    }
}
