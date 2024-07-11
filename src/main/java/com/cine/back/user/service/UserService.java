package com.cine.back.user.service;

import com.cine.back.user.dto.UserDTO;

public interface UserService {

    public UserDTO get(String userId);
}