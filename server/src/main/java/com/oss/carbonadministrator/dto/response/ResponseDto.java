/**
 *  Copyright 2022 Carbon_Developers
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
