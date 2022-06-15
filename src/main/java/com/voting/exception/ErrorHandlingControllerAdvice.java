package com.voting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
class ErrorHandlingControllerAdvice {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public StandardError exceptionHandler(ResourceNotFoundException e) {
        return new StandardError(e.getMessage());
    }

    @ExceptionHandler(value = {BusinessException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public StandardError exceptionHandler(BusinessException e) {
        return new StandardError(e.getMessage());
    }

    @ExceptionHandler(value = {DuplicatedKeyException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public StandardError exceptionHandler(DuplicatedKeyException e) {
        return new StandardError(e.getMessage());
    }

}
