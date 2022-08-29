package com.oss.carbonadministrator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException(String entityName, String msg) {
        super("ENF", entityName + ": " + msg);
    }
}
