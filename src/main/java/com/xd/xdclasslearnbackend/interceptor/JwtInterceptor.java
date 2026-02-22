package com.xd.xdclasslearnbackend.interceptor;

import com.xd.xdclasslearnbackend.entity.User;
import com.xd.xdclasslearnbackend.exception.BusinessException;
import com.xd.xdclasslearnbackend.service.UserService;
import com.xd.xdclasslearnbackend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author 康志阳
 * @date 2026/2/21 15:59
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        //访问评论区不需要token
        if(requestURI.matches(".*/api/courses/\\d+/commoents")&&"GET".equals(method)){
            return true;
        }

        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader!=null&&authorizationHeader.startsWith("Bearer ")){
           String token = authorizationHeader.substring(7);

           try{
               String username = jwtUtil.getUsernameFromToken(token);
               if(username != null && !jwtUtil.isTokenExpired(token)){
                   User user = userService.getUserByUsername(username);
                   if(user!=null){
                       request.setAttribute("currentUser",user);
                       return true;
                   }else {
                       response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                       response.getWriter().println("用户不存在");
                       return false;
                   }
               }else {
                   response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                   response.getWriter().println("令牌过期");
                   return false;
               }
           }catch (Exception e){
               response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
               response.getWriter().write("令牌验证失败");
               return false;
           }
        }else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("令牌验证失败");
            return false;
        }
    }
}
