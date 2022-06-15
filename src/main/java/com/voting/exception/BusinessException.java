package com.voting.exception;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -2500296013406553374L;

    public BusinessException(String msg) {
        super(msg);
    }
}