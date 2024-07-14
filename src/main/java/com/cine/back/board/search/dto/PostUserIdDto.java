package com.cine.back.board.search.dto;

import jakarta.validation.constraints.NotBlank;

public record PostUserIdDto(@NotBlank(message = "[NotBlank] 유저 아이디") String userId) {

}
