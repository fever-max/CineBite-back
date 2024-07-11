package com.cine.back.user.dto.oauth2;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import com.cine.back.user.dto.UserDTO;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails  implements UserDetails {

    private final UserDTO userDTO;

    public CustomUserDetails(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userDTO.getUserRole();
            }
        });
        return collection;
    }

    @Override
    public String getUsername() {
        return userDTO.getUserName();
    }

    @Override
    public String getPassword() {
        return userDTO.getUserPwd();
    }

    public String getUserNick() {
        return userDTO.getUserNick();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
