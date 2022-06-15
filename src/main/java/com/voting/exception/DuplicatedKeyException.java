package com.voting.exception;

public class DuplicatedKeyException extends RuntimeException {
    private static final long serialVersionUID = -2500296013406553374L;

    public DuplicatedKeyException(String msg) {
        super(msg);
    }
}