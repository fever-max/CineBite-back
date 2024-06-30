package com.cine.back.user.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cine.back.user.dto.UserDTO;
import com.cine.back.user.dto.oauth2.CustomUserDetails;
import com.cine.back.user.entity.UserEntity;
import com.cine.back.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserId(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        UserDTO userDto = UserDTO.builder()
                .userName(user.getUserId())
                .userPwd(user.getUserPwd())
                .userRole(user.getUserRole())
                .userNick(user.getUserNick())
                .build();      
        return new CustomUserDetails(userDto);
    }
}