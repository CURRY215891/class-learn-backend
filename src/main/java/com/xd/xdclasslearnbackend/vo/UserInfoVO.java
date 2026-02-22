package com.xd.xdclasslearnbackend.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 康志阳
 * @date 2026/2/20 16:22
 */
@Data
public class UserInfoVO {
    private long id;
    private String username;
    private String nickname;
    private String email;
    private String avatar;
    private LocalDateTime registerTime;
    private LocalDateTime lastLoginTime;
}
