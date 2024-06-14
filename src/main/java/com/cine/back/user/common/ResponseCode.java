package com.cine.back.user.common;

public enum ResponseCode {
    
    SUCCESS("SU"),
    VALIDATION_FAIL("VF"),
    DUPLICATE_ID("DI"),
    SIGN_IN_FAIL("SF"),
    CERTIFICATION_FAIL("CF"),
    MAIL_FAIL("MF"),
    DATABASE_ERROR("DBE");

    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}