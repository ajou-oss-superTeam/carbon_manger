package com.oss.carbonadministrator.exception.user;

public class AlreadyExistEmailException extends RuntimeException {

    public AlreadyExistEmailException(String message) {
        super(message);
    }
}
