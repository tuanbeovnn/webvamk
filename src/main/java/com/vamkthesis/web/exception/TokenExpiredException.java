package com.vamkthesis.web.exception;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends ClientException {
    public TokenExpiredException(String message) {
        super(HttpStatus.UNAUTHORIZED,message);
    }

    public TokenExpiredException() {
        super(HttpStatus.UNAUTHORIZED,"Token has been expired.");
    }
}
