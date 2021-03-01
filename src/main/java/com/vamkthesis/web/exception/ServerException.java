package com.vamkthesis.web.exception;

import org.springframework.http.HttpStatus;

public class ServerException extends CustomException {
    public ServerException(HttpStatus code, String message) {
        super(message);
        this.code = code;
    }

    public ServerException(String message) {
        super(message);
        this.code = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getCode() {
        return code;
    }
}
