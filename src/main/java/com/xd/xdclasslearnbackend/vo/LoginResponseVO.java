package com.xd.xdclasslearnbackend.vo;

import lombok.Data;

/**
 * @author 康志阳
 * @date 2026/2/20 16:21
 */
@Data
public class LoginResponseVO {
    private String accessToken;
    private String refreshToken;
    private UserInfoVO userInfo;
}
