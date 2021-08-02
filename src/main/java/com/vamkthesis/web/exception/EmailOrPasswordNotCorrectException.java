package com.vamkthesis.web.exception;

import org.springframework.http.HttpStatus;

public class EmailOrPasswordNotCorrectException extends ClientException {
    public EmailOrPasswordNotCorrectException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public EmailOrPasswordNotCorrectException() {
        super(HttpStatus.BAD_REQUEST, "Email or password not correct.");
    }
}
