package com.cine.back.user.common;

public interface ResponseMessage {
    
    String SUCCESS = "Success";

    String VALIDATION_FAIL = "Validation Failed";
    String DUPLICATE_ID = "Duplicate Id";

    String SIGN_IN_FAIL = "Login information mismatch";
    String CERTIFICATION_FAIL = "Certification Failed";

    String MAIL_FAIL = "Mail send failed";
    String DATABASE_ERROR = "Database error";
}
