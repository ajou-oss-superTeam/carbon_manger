package com.oss.carbonadministrator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class GeneralExceptions extends BaseException{
    public GeneralExceptions(String msg){
        super("GENERAL", msg);
    }
}
