package com.xd.xdclasslearnbackend.controller;

import com.xd.xdclasslearnbackend.commen.Result;
import com.xd.xdclasslearnbackend.dto.UserLoginDTO;
import com.xd.xdclasslearnbackend.entity.User;
import com.xd.xdclasslearnbackend.exception.BusinessException;
import com.xd.xdclasslearnbackend.service.UserService;
import com.xd.xdclasslearnbackend.util.JwtUtil;
import com.xd.xdclasslearnbackend.vo.LoginResponseVO;
import com.xd.xdclasslearnbackend.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 康志阳
 * @date 2026/2/20 16:20
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /*登录*/
    @PostMapping("/login")
    public Result<LoginResponseVO> login(@Validated @RequestBody UserLoginDTO userLoginDTO) {
       User user = userService.login(userLoginDTO.getUsername(),userLoginDTO.getPassword());
       if(user != null){
           /*生成token*/
           String accessToken = jwtUtil.generateToken(user.getUsername());
           String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
           UserInfoVO userInfoVO = new UserInfoVO();
           userInfoVO.setId(user.getId());
           userInfoVO.setUsername(user.getUsername());
           userInfoVO.setNickname(user.getNickname() !=null ? user.getNickname() : user.getUsername());
           userInfoVO.setAvatar(user.getAvatar() !=null ? user.getAvatar() :"https://xd.com");
           userInfoVO.setEmail(user.getEmail());
           userInfoVO.setRegisterTime(user.getCreatedTime());
           userInfoVO.setLastLoginTime(user.getUpdatedTime());

           LoginResponseVO loginResponseVO = new LoginResponseVO();
           loginResponseVO.setAccessToken(accessToken);
           loginResponseVO.setRefreshToken(refreshToken);
           loginResponseVO.setUserInfo(userInfoVO);
           return Result.success("登陆成功",loginResponseVO);

       }else{
           throw  new BusinessException(401,"用户名或密码错误");
       }


    }
}
