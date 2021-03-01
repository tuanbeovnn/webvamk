package com.vamkthesis.web.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class ClientException extends CustomException {
    public ClientException(HttpStatus code, String message, Map<String, Object> details) {
        super(code, message, details);
    }

    public ClientException(HttpStatus code, String message) {
        super(code, message);
    }

    public ClientException(int code) {
        super(code);
    }

    public ClientException(String message) {
        super(message);
    }

    public HttpStatus getCode() {
        return code;
    }
}
