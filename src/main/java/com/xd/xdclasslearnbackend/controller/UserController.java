package com.xd.xdclasslearnbackend.controller;

import com.xd.xdclasslearnbackend.commen.Result;
import com.xd.xdclasslearnbackend.entity.User;
import com.xd.xdclasslearnbackend.vo.UserInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关接口控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    /**
     * 获取当前登录用户信息
     * @param request HTTP请求，包含拦截器设置的currentUser
     * @return 用户信息VO
     */
    @GetMapping("/info")
    public Result<UserInfoVO> getUserInfo(HttpServletRequest request) {
        // 从请求域中获取当前用户（由JwtInterceptor设置）
        User user = (User) request.getAttribute("currentUser");

        // 如果拦截器配置正确，user不应为null；但为了安全起见，做个判断
        if (user == null) {
            return Result.error(401, "用户未登录或会话已过期");
        }

        // 构建返回给前端的UserInfoVO
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(user.getId());
        userInfoVO.setUsername(user.getUsername());
        // 如果没有昵称，默认使用用户名
        userInfoVO.setNickname(user.getNickname() != null ? user.getNickname() : user.getUsername());
        // 如果没有头像，使用默认头像
        userInfoVO.setAvatar(user.getAvatar() != null ? user.getAvatar() : "https://xd.com");
        userInfoVO.setEmail(user.getEmail());
        userInfoVO.setRegisterTime(user.getCreatedTime());
        userInfoVO.setLastLoginTime(user.getUpdatedTime());

        return Result.success("获取用户信息成功", userInfoVO);
    }
}