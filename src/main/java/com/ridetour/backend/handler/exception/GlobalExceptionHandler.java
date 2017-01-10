package com.ridetour.backend.handler.exception;

import com.ridetour.backend.response.ValidRestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by eyal on 5/27/2016.
 */
@ControllerAdvice
@RestController
class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BaseException.class)
    public String handleBaseException(BaseException e) {
        return e.getMessage();
    }

    @ExceptionHandler(value = Exception.class)
    public ValidRestResponse handleException(Exception e) {
        return ValidRestResponse.of(e.getMessage());
    }

}
