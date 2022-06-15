package com.voting.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2208159796744363130L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}