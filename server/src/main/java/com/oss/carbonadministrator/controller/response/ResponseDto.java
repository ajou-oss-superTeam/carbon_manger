package com.oss.carbonadministrator.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {

    private boolean success;
    private T data;
    private String message;
    private Error error;

    public static <T> ResponseDto success(T data, String message) {
        return new ResponseDto(true, data, message, null);
    }

    public static ResponseDto fail(Error error, String message) {
        return new ResponseDto(false, null, message, error);
    }

    @Getter
    @AllArgsConstructor
    public static class Error {

        private String code;
        private String errorMessage;
    }
}
