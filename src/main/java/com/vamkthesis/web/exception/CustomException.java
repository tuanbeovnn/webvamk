package com.vamkthesis.web.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class CustomException extends RuntimeException {
    public HttpStatus code;
    public Map<String, Object> details;

    public CustomException(HttpStatus code, String message, Map<String,Object> details) {
        super(message);
        this.code = code;
        this.details = details;
    }

    public CustomException(HttpStatus code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(HttpStatus code) {
        super(code.getReasonPhrase());
        this.code = code;
    }


    public CustomException(int code) {
        super(HttpStatus.valueOf(code).getReasonPhrase());
        this.code = HttpStatus.valueOf(code);
    }

    public CustomException(String message) {
        super(message);
        this.code = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getCode() {
        return code;
    }
}
