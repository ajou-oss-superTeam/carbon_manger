package com.oss.carbonadministrator.exception;

public class AlreadyExistEmailException extends RuntimeException {

    public AlreadyExistEmailException(String message) {
        super(message);
    }
}