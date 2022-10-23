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
package com.oss.carbonadministrator.dto.response.user;

import com.oss.carbonadministrator.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private String city;
    private String province;
    private String email;
    private String nickName;
    private int totalCount;

    public UserInfoResponse (User user){
        this.city = user.getCity();
        this.province = user.getProvince();
        this.email = user.getEmail();
        this.nickName = user.getNickname();
        this.totalCount = user.getElecCount() + user.getGasCount();
    }
}
