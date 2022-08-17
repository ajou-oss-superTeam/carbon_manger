package com.oss.carbonadministrator.exception;

import lombok.Getter;
import lombok.Setter;

public class BaseException extends RuntimeException{
    @Getter
    @Setter
    private String errorCode;

    @Getter
    @Setter
    private String userMessage;

    public BaseException(String errorCode, String userMessage){
        super(userMessage);
        this.errorCode = errorCode;
        this.userMessage = userMessage;
    }
}
