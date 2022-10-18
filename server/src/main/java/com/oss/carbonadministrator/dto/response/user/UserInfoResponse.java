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
        this.totalCount = user.getCount();
    }
}
