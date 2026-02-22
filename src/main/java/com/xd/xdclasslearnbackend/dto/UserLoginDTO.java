package com.xd.xdclasslearnbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author 康志阳
 * @date 2026/2/20 16:24
 */
@Data
public class UserLoginDTO {
    @NotBlank(message = "用户名不为空")
    @Size(min = 3,max = 20,message = "用户名长度大于3小于20")
    private  String username;

    @NotBlank(message = "密码不为空")
    @Size(min = 6,max = 20,message = "密码长度大于3小于20")
    private String password;
}
