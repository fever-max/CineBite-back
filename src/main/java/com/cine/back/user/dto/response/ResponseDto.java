package com.cine.back.user.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cine.back.user.common.ResponseCode;
import com.cine.back.user.common.ResponseMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {
    
    private String code;
    private String message;

    public ResponseDto() {

        this.code = ResponseCode.SUCCESS.getCode();
        this.message = ResponseMessage.SUCCESS.getMessage();
    }

    public static ResponseEntity<ResponseDto> databaseError() {

        ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR.getCode(), ResponseMessage.DATABASE_ERROR.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> validationFail() {

        ResponseDto responseBody = new ResponseDto(ResponseCode.VALIDATION_FAIL.getCode(), ResponseMessage.VALIDATION_FAIL.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
