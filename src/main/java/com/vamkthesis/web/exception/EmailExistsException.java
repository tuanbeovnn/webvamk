package com.vamkthesis.web.exception;

import java.util.HashMap;
import java.util.Map;

public class EmailExistsException extends ClientException {
    public EmailExistsException(String fieldName, String message) {
        super("Validations errors");
        Map<String, Object> details = new HashMap<>();
        details.put(fieldName,message);
        this.details = details;
//        this.code = HttpStatus.valueOf(405);
    }
    

    public EmailExistsException(int code) {
        super(code);

    }

    public EmailExistsException(String message) {
       this("email", message);
    }

    public EmailExistsException() {
        super("Email has been already exists.");
    }
}
