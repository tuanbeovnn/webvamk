package com.vamkthesis.web.config;


import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.exception.ClientException;
import com.vamkthesis.web.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity handleException(MethodArgumentNotValidException ex) {
        Map<String, String> details = new HashMap<>();
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError : errors) {
            if (details.get(fieldError.getField()) == null) details.put(fieldError.getField(), "");
            details.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntityBuilder.getBuilder()
                .setCode(HttpStatus.BAD_REQUEST)
                .setMessage("Validation errors")
                .setDetails(details)
                .build();
    }


    // token ko du quyen
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity processAccessDeniedExcpetion(AccessDeniedException e) {
        return ResponseEntityBuilder.getBuilder()
                .setCode(HttpStatus.FORBIDDEN)
                .setMessage(e.getMessage())
                .build();
    }

    // loi do ko ho tro phuong thuc
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return ResponseEntityBuilder.getBuilder()
                .setCode(HttpStatus.METHOD_NOT_ALLOWED)
                .setMessage(exception.getMessage())
                .build();
    }

    //    @ExceptionHandler(value = ClientException.class)
//    @ResponseBody
//    public ResponseEntity clientException(ClientException ex) throws Exception {
//        return ResponseEntityBuilder.getBuilder()
//                .setCode(ex.getCode())
//                .setMessage(ex.getMessage())
//                .build();
//    }
    //cu exception
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ResponseEntity> serverException(Throwable ex) {
        ResponseEntityBuilder builder = ResponseEntityBuilder.getBuilder()
                .setCode(ex instanceof CustomException ? ((CustomException) ex).code : HttpStatus.INTERNAL_SERVER_ERROR)
                .setMessage(ex.getMessage());
        if (ex instanceof ClientException) {
            ClientException exception = (ClientException) ex;
            if (exception.details != null) {
                builder.setDetails((exception.details));
            }
        }
        return builder.build();
    }
}


