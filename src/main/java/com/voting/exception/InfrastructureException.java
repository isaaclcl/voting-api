package com.voting.exception;

public class InfrastructureException extends RuntimeException {
    private static final long serialVersionUID = -3584504846913702053L;
    public InfrastructureException(String msg) {
        super(msg);
    }
}
