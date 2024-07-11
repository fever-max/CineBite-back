package com.cine.back.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.cine.back.user.dto.UserDTO;
import com.cine.back.user.dto.request.PasswordValidationRequest;
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

    @PostMapping("/validatePassword")
    public ResponseEntity<Boolean> validatePassword(@RequestBody PasswordValidationRequest request) {
        boolean isValid = userService.validatePassword(request.getUserId(), request.getUserPwd());
        return ResponseEntity.ok(isValid);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser( @RequestPart("user") UserDTO user) {
        try {
            UserDTO updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating user information");
        }
    }
}
