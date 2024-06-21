package com.cine.back.user.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cine.back.user.common.ResponseCode;
import com.cine.back.user.common.ResponseMessage;

import lombok.Getter;

@Getter
public class IdCheckResponseDto extends ResponseDto {
    
    private IdCheckResponseDto() {
        super();
    }

    public static ResponseEntity<IdCheckResponseDto> success() {
        IdCheckResponseDto responseBody = new IdCheckResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> duplicateId() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_ID.getCode(), ResponseMessage.DUPLICATE_ID.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
