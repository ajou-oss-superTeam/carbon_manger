package com.oss.carbonadministrator.dto.response;

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

    public static <T> ResponseDto success(T data, String userMessage) {
        return new ResponseDto(true, data, userMessage, null);
    }

    public static ResponseDto fail(String userMessage, String errorCode, String errorMessage) {
        return new ResponseDto(false, null, userMessage, new Error(errorCode, errorMessage));
    }

    @Getter
    @AllArgsConstructor
    public static class Error {

        private String errorCode;
        private String errorMessage;
    }
}
